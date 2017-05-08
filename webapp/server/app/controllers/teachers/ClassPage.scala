package controllers.teachers

import controllers.services.DBExecute._
import controllers.teachers.auth.{TeacherAuthConfigTrait, TeacherRole}
import jp.t2v.lab.play2.auth.AuthElement
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Controller
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.PupilsRow
import teacherapps.dbmodels.Tables.profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import com.github.t3hnar.bcrypt.Password

class ClassPage extends Controller with AuthElement with TeacherAuthConfigTrait {

  def index(class_id : Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn


    val class_query = (for{
      classes <- Tables.Classes
      if(classes.id === class_id)
    } yield classes).take(1).result.head

    val selected_class =  Await.result(dbrun(class_query), Duration.Inf)


    val pupil_query = (for{
      pupils <- Tables.Pupils
      if(pupils.classId ===class_id)
      if(pupils.disabled===false)
    } yield pupils).result



    dbrun(pupil_query).map{ pupils =>
      Ok(views.html.teachers.ClassPage(account, selected_class, pupils))
    }

  }




  def insertPupil(class_id:Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn

    case class formData(firstName: String, lastName: String, login: String, password: String, confirmPassword: String)


    val form = Form(mapping("first_name" -> text, "last_name" -> text,"login" -> text, "password" -> text, "confirm_password" -> text)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.ClassPage.index(class_id))} }  ,
      success => {
        if(success.password ==success.confirmPassword && success.password!="") {
          val row = new PupilsRow(
                  id = -1,
                  firstName = success.firstName,
                  lastName = success.lastName,
                  login = success.login,
                  password = success.password.bcrypt,
                  classId = class_id,
                  disabled = false,
                  createdAt = java.time.ZonedDateTime.now,
                  updatedAt = java.time.ZonedDateTime.now

                )


          dbrun((Tables.Pupils returning Tables.Pupils.map(_.id)) += row)
        }
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.ClassPage.index(class_id))
    }
  }


  def disableClass(class_id: Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(class_id: Int)

    val account = loggedIn

    val form = Form(mapping("class_id" -> number)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.ClassPage.index(class_id))} }  ,
      success => {

        val query = for {
          selected_class <- dbrun(Tables.Classes.filter(_.id === success.class_id).take(1).result.head)
          _ <- dbrun {
            Tables.Classes.filter(_.id === class_id).update(
              selected_class.copy(
                  disabled = true
                )
            )
          }
        } yield None
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.ClassPage.index(class_id))
    }
  }



  def enableClass(class_id: Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(class_id: Int)

    val account = loggedIn

    val form = Form(mapping("class_id" -> number)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.ClassPage.index(class_id))} }  ,
      success => {

        val query = for {
          selected_class <- dbrun(Tables.Classes.filter(_.id === success.class_id).take(1).result.head)
          _ <- dbrun {
            Tables.Classes.filter(_.id === class_id).update(
              selected_class.copy(
                disabled = false
              )
            )
          }
        } yield None
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.ClassPage.index(class_id))
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


