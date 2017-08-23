package controllers.teachers

import java.io.File

import controllers.services.DBExecute._
import controllers.services.ajax.AjaxServerHandler
import controllers.services.ajax.AjaxServerHandler.AutowireContext
import controllers.teachers.auth.{TeacherAuthConfigTrait, TeacherRole}
import jp.t2v.lab.play2.auth.AuthElement
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.{PDPageContentStream, PDPage, PDResources, PDDocument}
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Controller
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._
import teachers.{SmallTextBox, AjaxApi_OperationPage}
import upickle.default._
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


class OperationPage extends Controller with AuthElement with TeacherAuthConfigTrait {

  def index() = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn

    Future.successful{
      Ok(views.html.teachers.OperationPage(account))
    }

  }


  def download() = AsyncStack(AuthorityKey -> TeacherRole.Teacher) { implicit request =>
    val account = loggedIn

    /*case class formData(text: String)

    println("HEREEE 66")
    val form = Form(mapping("text" -> text)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  {
        println("HEREEE 88")
        Future.successful{Redirect(controllers.teachers.routes.OperationPage.index())}
      }   ,
      success => {

        println(success.text)
        var boxList = read[Seq[SmallTextBox]](success.text)

        println("HEREEE 99")
        createPDFPage(boxList)
        Future.successful {
          Ok.sendFile(new File("/home/loick/smalltextbox.pdf"), inline = true).withHeaders(CACHE_CONTROL -> "max-age=3600", CONTENT_DISPOSITION -> "attachment; filename=smalltextbox.pdf", CONTENT_TYPE -> "application/x-download");
        }
      }
    )*/
    Future.successful {
      Ok.sendFile(new File("/home/loick/smalltextbox.pdf"), inline = true).withHeaders(CACHE_CONTROL -> "max-age=3600", CONTENT_DISPOSITION -> "attachment; filename=smalltextbox.pdf", CONTENT_TYPE -> "application/x-download");
    }
  }



  type ajaxApiTrait = AjaxApi_OperationPage
  def ajaxAPI = AjaxServerHandler.api(AjaxServer)_


  object AjaxServer extends AjaxServerHandler.AutowirePlayServer[ajaxApiTrait] {
    case class ApiInstance() extends ajaxApiTrait{
      override def createPDF(boxList : Seq[SmallTextBox])={

        println("HERE 777")
        createPDFPage(boxList)

      }

    }
    val apiService = new ApiInstance()
    override def routes(target: ajaxApiTrait) = route[ajaxApiTrait](apiService)
    override def createImpl(autowireContext: AutowireContext): ajaxApiTrait = new AjaxServerImpl(autowireContext)
    class AjaxServerImpl(context: AutowireContext) extends ajaxApiTrait {

      override def createPDF(boxList : Seq[SmallTextBox])= {
        createPDFPage(boxList)
      }
      /* override def getScreenshotUrl(serverDirectory: String): String = {
         return controllers.admin.routes.ExtractionDebuggerPage.getScreenshotFile(serverDirectory).toString
       }*/
    }
  }


  def createPDFPage(boxList : Seq[SmallTextBox]): Unit = {

    //Loading an existing document
    val document = new PDDocument()
    println("PDF HERE")

    val docCatalog = document.getDocumentCatalog();
    val acroForm = new PDAcroForm(document)

    val res : PDResources = new PDResources();

    //val fontStream = getClass().getResourceAsStream("LiberationSans-Regular.ttf");
    val font = PDType0Font.load(document, new File("/home/loick/Pacifico.ttf"))
    val fontName = res.add(font)
    acroForm.setDefaultResources(res);

    //Creating a PDF Document
    val page = new PDPage()


    val contentStream = new PDPageContentStream(document, page);

    boxList.foreach { box =>
      contentStream.beginText();
      //contentStream.newLineAtOffset(610,771);
      contentStream.newLineAtOffset((0.9576*box.x).toInt, (771-(0.9576*box.y).toInt));
      contentStream.setFont(font, box.fontsize);
      //contentStream.setLeading(14.5f);
      contentStream.showText(box.text);
      contentStream.endText();
    }

    contentStream.close()

    document.addPage(page)
    //Saving the document
    document.save(new File("/home/loick/smalltextbox.pdf"));

    //Closing the document
    document.close();

    Ok.sendFile(new File("/home/loick/smalltextbox.pdf"), inline = true).withHeaders(CACHE_CONTROL -> "max-age=3600", CONTENT_DISPOSITION -> "attachment; filename=smalltextbox.pdf", CONTENT_TYPE -> "application/x-download");
  }
}


