package teacherapps.dbmodels

import slick.profile.SqlProfile.ColumnOption
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object SourceCodeGenerator extends App  {

  val url = "jdbc:postgresql://localhost:43214/teacherapps"
  val user = "teacherapp_admin"
  val password = "aEr56yUI.,"
  val jdbcDriver = "org.postgresql.Driver"
  val slickDriver = "teacherapps.dbmodels.TeacherAppsDriver"
  val outputFolder = "webapp/slick/src/main/scala"
  val pkg = "teacherapps.dbmodels"

  val db = TeacherAppsDriver.api.Database.forURL(url, driver = jdbcDriver, user = user, password = password, keepAliveConnection = true)

  //the table spatial_ref_sys is an internal table of the postgis extension
  val modelAction = TeacherAppsDriver.createModel(Some(TeacherAppsDriver.defaultTables))

  val codegen = db.run(modelAction).map { model =>

    new slick.codegen.SourceCodeGenerator(model) {
      override def Table = new Table(_) { table =>

        override def Column = new Column(_) { column =>
          // customize db type -> scala type mapping, pls adjust it according to your environment
          override def rawType: String = model.tpe match {
            case "java.sql.Date" => "java.time.LocalDate"
            case "java.sql.Time" => "java.time.LocalTime"
            case "java.sql.Timestamp" => "java.time.ZonedDateTime"
            case "String" => model.options.find(_.isInstanceOf[ColumnOption.SqlType])
              .map(_.asInstanceOf[ColumnOption.SqlType].typeName).map({
            case "hstore" => "Map[String, String]"
            case "text" => "String"
            case "varchar" => "String"
              case unknow =>  {
                println(s"------------------------------------------------------ ERROR! Undefined type [${unknow}]")
                throw new IllegalArgumentException(s"Undefined type [${unknow}]")
              }

            }).getOrElse("String")
            case _ => super.rawType.asInstanceOf[String]
          }
        }
      }

      override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]) : String = {
        s"""
package ${pkg}

// AUTO-GENERATED Slick data model [${java.time.ZonedDateTime.now()}]

/** Stand-alone Slick data model for immediate use */
object ${container}${parentType.map(t => s" extends $t").getOrElse("")} {
  val profile = $profile
  import profile.api._
  ${indent(code)}
}
              """.trim()
      }
    }
  }

  Await.ready(codegen.map(_.writeToFile(slickDriver, outputFolder, pkg, "Tables", "Tables.scala")), Duration.Inf)
}
