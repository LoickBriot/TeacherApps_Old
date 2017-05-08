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
import scala.concurrent.Future
import com.github.t3hnar.bcrypt.Password

class PupilPage extends Controller with AuthElement with TeacherAuthConfigTrait {

  def index(pupil_id : Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn


    val query = (for{
      pupils <- Tables.Pupils
      classes <- Tables.Classes
      if(pupils.id === pupil_id)
      if(classes.id===pupils.classId)
    } yield (classes, pupils)).result


    dbrun(query).map{ pupils_and_classes =>

      val selected_class = pupils_and_classes.head._1
      val pupil = pupils_and_classes.head._2
    Ok(views.html.teachers.PupilPage(account, selected_class, pupil))
    }

  }


  def updatePupil(pupil_id:Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(id:Int, firstName: String, lastName: String, login: String, password: String, confirmPassword: String)

    val account = loggedIn

    val form = Form(mapping("id" -> number, "first_name" -> text, "last_name" -> text,"login" -> text, "password" -> text, "confirm_password" -> text)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.PupilPage.index(pupil_id))} }  ,
      success => {
          val query = for {
            user <- dbrun(Tables.Pupils.filter(_.id === success.id).take(1).result.head)
            _ <- dbrun {
              Tables.Pupils.filter(_.id === pupil_id).update(
                if (success.password == success.confirmPassword && success.password != "") {
                  user.copy(
                    firstName = success.firstName,
                    lastName = success.lastName,
                    login = success.login,
                    password = success.password.bcrypt
                  )
                } else {
                  user.copy(
                    firstName = success.firstName,
                    lastName = success.lastName,
                    login = success.login
                  )
                }
              )
            }
          } yield None
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.PupilPage.index(pupil_id))
    }
  }


  def disablePupil(pupil_id: Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(pupil_id: Int)

    val account = loggedIn

    val form = Form(mapping("pupil_id" -> number)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.PupilPage.index(pupil_id))} }  ,
      success => {

        val query = for {
          selected_pupil <- dbrun(Tables.Pupils.filter(_.id === success.pupil_id).take(1).result.head)
          _ <- dbrun {
            Tables.Pupils.filter(_.id === pupil_id).update(
              selected_pupil.copy(
                disabled = true
              )
            )
          }
        } yield None
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.PupilPage.index(pupil_id))
    }
  }


  def enablePupil(pupil_id: Int) = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    case class formData(pupil_id: Int)

    val account = loggedIn

    val form = Form(mapping("pupil_id" -> number)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  { Future.successful{Redirect(controllers.teachers.routes.PupilPage.index(pupil_id))} }  ,
      success => {

        val query = for {
          selected_pupil <- dbrun(Tables.Pupils.filter(_.id === success.pupil_id).take(1).result.head)
          _ <- dbrun {
            Tables.Pupils.filter(_.id === pupil_id).update(
              selected_pupil.copy(
                disabled = false
              )
            )
          }
        } yield None
      }
    )
    Future.successful{
      Redirect(controllers.teachers.routes.PupilPage.index(pupil_id))
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


