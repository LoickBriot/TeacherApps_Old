package controllers.teachers.auth

import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc.Controller

class TeacherRedirectionController extends Controller with AuthElement with TeacherAuthConfigTrait {

  def index() = StackAction(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
      Redirect(controllers.teachers.routes.HomePage.index())

  }

}