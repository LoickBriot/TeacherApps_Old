package controllers.pupils

import java.time.{Duration, ZonedDateTime}

import controllers.pupils.auth.{PupilAuthConfigTrait, PupilRole}
import controllers.services.DBExecute._
import controllers.services.ajax.AjaxServerHandler
import controllers.services.ajax.AjaxServerHandler.AutowireContext
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._
import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc.Controller
import shared.{AjaxApi_WelcomePage, StateMap, WelcomeResponse}
import upickle.Js

import scala.collection.mutable
import scala.collection.parallel.immutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class HomePage extends Controller with AuthElement with PupilAuthConfigTrait {

  def index() = AsyncStack(AuthorityKey -> PupilRole.Pupils) { implicit request =>
    val account = loggedIn


    Future.successful {
      Ok(views.html.pupils.HomePage(account))
    }

  }




  /* type ajaxApiTrait = AjaxApi_WelcomePage

   def ajaxAPI = AjaxServerHandler.api(AjaxServer) _


   implicit val thing2Writer = upickle.default.Writer[EnWebworkerClientsState.Value]{
     t => Js.Str(t.toString)
   }

   object AjaxServer extends AjaxServerHandler.AutowirePlayServer[ajaxApiTrait] with LocalDatabaseRunner {

     case class ApiInstance() extends ajaxApiTrait {
       override def getProjectsTasksStates(elements: String, dateFilter: String): Seq[WelcomeResponse] = getProjectsTasksStatesImp(elements, dateFilter)
     }

     val apiService = ApiInstance()

     override def routes(target: ajaxApiTrait) = route[ajaxApiTrait](apiService)

     override def createImpl(autowireContext: AutowireContext): ajaxApiTrait = new AjaxServerImpl(autowireContext)


     class AjaxServerImpl(context: AutowireContext) extends ajaxApiTrait {
       override def getProjectsTasksStates(elements: String, dateFilter: String): Seq[WelcomeResponse] = getProjectsTasksStatesImp(elements, dateFilter)
     }

   }

  */
}


