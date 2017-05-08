package controllers.pupils.auth

import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc.Controller

class PupilRedirectionController extends Controller with AuthElement with PupilAuthConfigTrait {

  def index() = StackAction(AuthorityKey -> PupilRole.Pupils) { implicit request =>
      Redirect(controllers.pupils.routes.HomePage.index())

  }

}