package controllers.teachers

import java.io.File

import controllers.services.DBExecute._
import controllers.services.ajax.AjaxServerHandler
import controllers.services.ajax.AjaxServerHandler.AutowireContext
import controllers.teachers.auth.{TeacherAuthConfigTrait, TeacherRole}
import jp.t2v.lab.play2.auth.AuthElement
import org.apache.pdfbox.pdmodel.font.{PDType0Font, PDType1Font,PDType3Font, PDSimpleFont}
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream, PDResources}
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Controller
import teacherapps.dbmodels.Tables
import teacherapps.dbmodels.Tables.profile.api._
import teachers.{AjaxApi_OperationPage, JSONLine, JSONTextBox}
import upickle.default._

import scala.concurrent.{Await, Future}
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

    case class formData(download_type: String)

    val form = Form(mapping("download_type" -> text)(formData.apply)(formData.unapply))
    form.bindFromRequest().fold(
      errors =>  {
        Future.successful {
          Ok("")
        }
      } ,
      success => {

        Future.successful {
          Ok.sendFile(
            success.download_type match {
              case "correction" =>new File("/home/loick/smalltextbox_correction.pdf");
              case "exercice" => new File("/home/loick/smalltextbox_exercice.pdf");
              case _ => new File("/home/loick/smalltextbox_exercice.pdf");
            }
            , inline = true).withHeaders(CACHE_CONTROL -> "max-age=3600", CONTENT_DISPOSITION -> "attachment; filename=smalltextbox.pdf", CONTENT_TYPE -> "application/x-download");
        }
      })
  }



    type ajaxApiTrait = AjaxApi_OperationPage
    def ajaxAPI = AjaxServerHandler.api(AjaxServer)_


    object AjaxServer extends AjaxServerHandler.AutowirePlayServer[ajaxApiTrait] {
      case class ApiInstance() extends ajaxApiTrait{
        override def createPDF(correction : Boolean, boxList : Seq[JSONTextBox], lines : Seq[JSONLine])={

          //println("HERE 777")
          createPDFPage(correction, boxList, lines)

        }

      }
      val apiService = new ApiInstance()
      override def routes(target: ajaxApiTrait) = route[ajaxApiTrait](apiService)
      override def createImpl(autowireContext: AutowireContext): ajaxApiTrait = new AjaxServerImpl(autowireContext)
      class AjaxServerImpl(context: AutowireContext) extends ajaxApiTrait {

        override def createPDF(correction : Boolean ,boxList : Seq[JSONTextBox], lines : Seq[JSONLine])= {
          createPDFPage(correction, boxList, lines)
        }
        /* override def getScreenshotUrl(serverDirectory: String): String = {
           return controllers.admin.routes.ExtractionDebuggerPage.getScreenshotFile(serverDirectory).toString
         }*/
      }
    }


    def createPDFPage(correction : Boolean, boxList : Seq[JSONTextBox], lines : Seq[JSONLine]): Unit = {

      //Loading an existing document
      val document = new PDDocument()
      println("PDF HERE")

      val docCatalog = document.getDocumentCatalog();
      val acroForm = new PDAcroForm(document)

      val res : PDResources = new PDResources();

      //val fontStream = getClass().getResourceAsStream("LiberationSans-Regular.ttf");

      //Creating a PDF Document
      val page = new PDPage()


      val contentStream = new PDPageContentStream(document, page);

      boxList.foreach { box =>
        val font = box.font match{
          case "pacifico" => PDType0Font.load(document, new File("/home/loick/Pacifico.ttf"))
          case "courier" => PDType1Font.COURIER
          case _ => PDType1Font.HELVETICA
        }
        //PDType0Font.load(document, new File("/home/loick/Pacifico.ttf"))
        val fontName = res.add(font)
        acroForm.setDefaultResources(res);


        contentStream.beginText();
        //contentStream.newLineAtOffset(610,771);
        contentStream.newLineAtOffset((0.9576*box.x).toInt, (771-(0.9576*box.y).toInt));
        contentStream.setFont(font, box.fontsize);
        //contentStream.setLeading(14.5f);
        contentStream.showText(box.text);
        contentStream.endText();
      }

      lines.foreach{ line =>

        contentStream.addRect((0.9576*line.x).toInt, (785-(0.9576*line.y).toInt), (0.9576*line.width).toInt+1, (0.9576*line.height).toInt+1)
        contentStream.fill();
      }

      contentStream.close()

      document.addPage(page)
      //Saving the document
      document.save(
        if(correction){
          new File("/home/loick/smalltextbox_correction.pdf");
        } else {
          new File("/home/loick/smalltextbox_exercice.pdf");
        }
      )

      //Closing the document
      document.close();
    }
  }


