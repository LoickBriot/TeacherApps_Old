package client.teachers

import client.teachers.OperationPageJS.{ajaxClientHandler, canvas}
import facades.fabricjs
import facades.fabricjs.{Group, Line, Textbox}
import org.scalajs.dom.document
import autowire._
import teachers.{JSONLine, JSONTextBox}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by loick on 27/08/17.
  */
object PDFUtils {


  def createPDF(): Unit = {

    val JSONTextBoxes_exercice = ListBuffer[JSONTextBox]()
    val JSONLines_exercice = ListBuffer[JSONLine]()

    val JSONTextBoxes_correction = ListBuffer[JSONTextBox]()
    val JSONLines_correction = ListBuffer[JSONLine]()

    canvas.getObjects().foreach(obj => recursive_loop(obj,true) )
    canvas.getObjects().foreach(obj => recursive_loop(obj,false) )

    def recursive_loop(obj: fabricjs.Object, correction : Boolean, limit : Int = -1){
      obj.get("type") match {
        case "textbox" => {
          val text = obj.asInstanceOf[Textbox]
          (if(correction) JSONTextBoxes_correction else JSONTextBoxes_exercice ).append(new JSONTextBox(
            if(!correction && limit>=0 && text.getTop()>limit) "_" else text.getText().toString,
            text.getLeft().toInt,
            text.getTop().toInt,
            text.getWidth().toInt,
            text.getHeight().toInt,
            text.getFontSize().toInt,
            text.getFontFamily().toString,
            text.getTextBackgroundColor().toString,
            text.getTextAlign().toString,
            if (document.getElementById("fontWeight").getAttribute("value").isEmpty) "" else "bold",
            text.getFontStyle().toString,
            text.getTextDecoration().toString
          ))
        }
        case "line" => {
          val line = obj.asInstanceOf[Line]
          (if(correction) JSONLines_correction else JSONLines_exercice).append(new JSONLine(
            line.getLeft().toInt,
            line.getTop().toInt,
            line.getWidth().toInt,
            line.getHeight().toInt
          ))
        }
        case "group" => {
          val group = obj.asInstanceOf[Group]
          group._restoreObjectsState()
          val top_limit = group.getObjects("line").sortBy(_.getTop()).map(_.getTop()).headOption.getOrElse(-1.0d).toInt
          group.getObjects().foreach( obj2 => recursive_loop(obj2,correction, top_limit))
        }
        case _ =>
      }
    }


    ajaxClientHandler.createPDF(false, JSONTextBoxes_exercice, JSONLines_exercice).call().onSuccess{ case result =>
      println("PDF1 generated !")
    }

    ajaxClientHandler.createPDF(true, JSONTextBoxes_correction, JSONLines_correction).call().onSuccess{ case result =>
      println("PDF1 generated !")
    }
  }

}
