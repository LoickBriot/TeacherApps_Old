package controllers.services

import teacherapps.dbmodels.TeacherAppsDriver.api.Database
import slick.dbio.{DBIOAction, NoStream}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object DBExecute {

  private val db = Database.forURL(url="jdbc:postgresql://localhost:43214/teacherapps",user ="teacherapp_admin", password = "aEr56yUI.,", driver = "org.postgresql.Driver")

  def dbrun[R](a: DBIOAction[R, NoStream, Nothing]): Future[R] = {
    db.run(a)
  }

  def dbrunSync[R](a: DBIOAction[R, NoStream, Nothing]):R = {
    Await.result(dbrun(a), Duration.Inf)
  }
}
