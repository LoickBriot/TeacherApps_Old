package controllers.pupils.auth

import com.github.t3hnar.bcrypt.Password
import controllers.services.DBExecute
import controllers.services.DBExecute._
import jp.t2v.lab.play2.auth.{LoginLogout, OptionalAuthElement}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

case class PupilLoginAccountData( login:String, password:String)

class PupilAuthController extends Controller with OptionalAuthElement with LoginLogout with PupilAuthConfigTrait {

  private val loginForm = Form(
    mapping(
      "login" -> text,
      "password" -> text
    )(PupilLoginAccountData.apply)(PupilLoginAccountData.unapply)
  )

  def prepareLogin() = StackAction { implicit request =>
    if(loggedIn.isDefined) {
      Redirect(controllers.pupils.auth.routes.PupilRedirectionController.index())
    } else {
      Ok(views.html.pupils.login(loginForm))
    }
  }

  def logout = Action.async { implicit request =>
    gotoLogoutSucceeded
  }

  def login() = Action.async { implicit request =>

    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.pupils.login(formWithErrors))),
      account => selectAccountId(account.login, account.password).map {
        case Some(id) => Await.result(gotoLoginSucceeded(id), Duration.Inf)
        case _ => Redirect(controllers.pupils.auth.routes.PupilAuthController.login()).flashing("error" -> "flash.wrong_login")
      }
    )

  }


   private def selectAccountId(login:String, password:String) = {
    val query = (for {
      row <- Tables.Pupils.filter( x=> x.login === login)
    } yield (row.id, row.password)).take(1).result.headOption

    //headOption because we only want 1 result
    dbrun(query.map {
      case Some((id, encryptedPassword)) => {
        if(encryptedPassword.size == 60 && password.isBcrypted(encryptedPassword)) Some(id)
        else None
      }
      case None => None
    })
  }
}
