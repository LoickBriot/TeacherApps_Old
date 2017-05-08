package controllers.teachers

import controllers.services.DBExecute._
import controllers.teachers.auth.{TeacherAuthConfigTrait, TeacherRole}
import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc.Controller
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


class DisabledPage extends Controller with AuthElement with TeacherAuthConfigTrait {

  def index() = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn


    val class_query = (for{
      teacher <- Tables.Teachers
      classes <- Tables.Classes
      if(teacher.login===account.login)
      if(classes.teacherId === teacher.id)
    } yield classes).result

    val classes =  Await.result(dbrun(class_query), Duration.Inf)


    val pupil_query = (for{
      pupils <- Tables.Pupils
      if(pupils.classId inSetBind classes.map(_.id))
    } yield pupils).result



    dbrun(pupil_query).map{ pupils =>

      val classes_and_pupils = classes.map(el => (el, pupils.filter(_.classId==el.id).size )).toSeq
      Ok(views.html.teachers.DisabledPage(account, classes.filter(_.disabled), pupils.filter(_.disabled)))
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


