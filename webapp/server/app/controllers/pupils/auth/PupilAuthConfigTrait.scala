package controllers.pupils.auth

import controllers.services.DBExecute
import controllers.services.DBExecute._
import jp.t2v.lab.play2.auth.{AuthConfig, CookieTokenAccessor}
import play.api.mvc.Results._
import play.api.mvc.{RequestHeader, Result}
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect._


sealed trait PupilRole

object PupilRole {
  case object Pupils extends PupilRole

  def apply(roleName:String):PupilRole = roleName match {
    case _ => Pupils
  }
}

/**
  * Created by loick on 3/25/16.
  */
private[controllers] trait PupilAuthConfigTrait extends AuthConfig {

  /**
    * A type that is used to identify a user.
    * `String`, `Int`, `Long` and so on.
    */
  type Id = Int

  /**
    * A type that representss a user in your application.
    * `User`, `Account` and so on.
    */
  type User = Tables.PupilsRow


  /**
    * A type that is defined by every action for authorization.
    * This sample uses the following trait:
    *
    * sealed trait Role
    * case object Administrator extends Role
    * case object NormalUser extends Role
    */
  type Authority = PupilRole


  /**
    * A `ClassTag` is used to retrieve an id from the Cache API.
    * Use something like this:
    */
  val idTag: ClassTag[Id] = classTag[Id]

  /**
    * The session timeout in seconds
    */
  val sessionTimeoutInSeconds: Int = 3600

  /**
    * A function that returns a `User` object from an `Id`.
    * You can alter the procedure to suit your application.
    */
  def resolveUser(id: Id)(implicit ctx: ExecutionContext): Future[Option[User]] = dbrun(Tables.Pupils.filter(_.id === id).take(1).result.headOption)

  /**
    * Where to redirect the user after a successful login.
    */
  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext): Future[Result] = {
    Future.successful(Redirect(controllers.pupils.auth.routes.PupilRedirectionController.index()))
  }

  /**
    * Where to redirect the user after logging out
    */
  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext): Future[Result] = {
    Future.successful(Redirect(controllers.routes.Application.index()))
  }

  /**
    * If the user is not logged in and tries to access a protected resource then redirect them as follows:
    */
  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext): Future[Result] =
    Future.successful(Redirect(controllers.pupils.auth.routes.PupilAuthController.login()))

  /**
    * If authorization failed (usually incorrect password) redirect the user as follows:
    */
  override def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] = {
    Future.successful(Redirect(controllers.pupils.auth.routes.PupilAuthController.login()).flashing("error" -> "flash.wrong_login"))
  }

  /**
    * A function that determines what `Authority` a user has.
    * You should alter this procedure to suit your application.
    */
  def authorize(user: User, authority: Authority)(implicit ctx: ExecutionContext): Future[Boolean] = Future.successful {
    true
  }

  /**
    * (Optional)
    * You can custom SessionID Token handler.
    * Default implementation use Cookie.
    */
  override lazy val tokenAccessor = new CookieTokenAccessor(
    cookieSecureOption = false,
    cookieMaxAge = None
  )
}
