package controllers.teachers

import controllers.services.DBExecute._
import controllers.teachers.auth.{TeacherAuthConfigTrait, TeacherRole}
import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc.Controller
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Controller
import com.github.t3hnar.bcrypt.Password

class AdminPage extends Controller with AuthElement with TeacherAuthConfigTrait {

  def index() = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn


    Future.successful{
      Ok(views.html.teachers.AdminPage(account))
    }

  }



  def update() = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(firstName: String, lastName: String, login: String, email: String, password: String, confirmPassword: String)

    val account = loggedIn

    val form = Form(mapping("first_name" -> text, "last_name" -> text,"login" -> text, "email" -> text, "password" -> text, "confirm_password" -> text)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.AdminPage.index())} }  ,
      success => {

        val query = for {
          user <- dbrun(Tables.Teachers.filter(_.id === account.id).take(1).result.head)
          _ <- dbrun {
            Tables.Teachers.filter(_.id === account.id).update(
              if(success.password ==success.confirmPassword && success.password!="") {
                user.copy(
                  firstName = success.firstName,
                  lastName = success.lastName,
                  login = success.login,
                  email = success.email,
                  password = success.password.bcrypt

                )
              } else {
                user.copy(
                  firstName = success.firstName,
                  lastName = success.lastName,
                  login = success.login,
                  email = success.email
                )
              }
            )
          }
        } yield None

        query.map { _ =>
          Redirect(controllers.teachers.routes.AdminPage.index())
        }
      }
    )
  }

  def insertNewClass() = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(class_name: String)

    val account = loggedIn

    val form = Form(mapping("class_name" -> text)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.AdminPage.index())} }  ,
      success => {
        if(success.class_name!=""){
          val row = new Tables.ClassesRow(
            id = -1,
            name = success.class_name,
            teacherId = account.id,
            disabled = false,
            createdAt = java.time.ZonedDateTime.now,
            updatedAt = java.time.ZonedDateTime.now
          )

          dbrun((Tables.Classes returning Tables.Classes.map(_.id)) += row)
        }
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.AdminPage.index())
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


