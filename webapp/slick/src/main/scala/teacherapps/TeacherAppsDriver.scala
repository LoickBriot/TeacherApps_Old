package teacherapps.dbmodels

import com.github.tminglei.slickpg._
import slick.ClassLevels

trait TeacherAppsDriver extends ExPostgresDriver
  with PgArraySupport
  with PgEnumSupport
  with PgRangeSupport
  with PgDate2Support
  with PgHStoreSupport
  with PgSearchSupport
  with PgNetSupport
  with PgLTreeSupport {

  override val api = TetraoAPI

  object TetraoAPI extends API with ArrayImplicits
    with DateTimeImplicits
    with NetImplicits
    with LTreeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants {

    implicit val tEnTaskExecutionsState = createEnumJdbcType("class_levels", ClassLevels)
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}

object TeacherAppsDriver extends TeacherAppsDriver
