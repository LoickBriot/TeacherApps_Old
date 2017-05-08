package controllers

import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

class Application extends Controller {

  def index = Action { implicit request =>

    Ok(views.html.common.WelcomePage())
  }

  def goToTeacherAuthentication = Action { implicit request =>
    Redirect(controllers.teachers.auth.routes.TeacherAuthController.login())
  }

  def goToPupilsAuthentication = Action { implicit request =>
    Redirect(controllers.pupils.auth.routes.PupilAuthController.login())
  }
}

