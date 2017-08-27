package client.teachers


/**
  * Created by lbriot on 10/17/16.
  */

import facades.fabricjs
import facades.fabricjs.Textbox
import services.ajax.AjaxClientHandler
import org.scalajs.dom.{KeyboardEvent, document}
import teachers._

import scala.scalajs.js.JSApp
import org.scalajs.jquery.jQuery

import scala.scalajs.js.annotation.JSExport



object OperationPageJS extends JSApp {



  @JSExport
  def main(): Unit = {}


  val ajaxClientHandler = new AjaxClientHandler("/operation/ajaxapi/")[AjaxApi_OperationPage]


  val fontMap = Map(
    "arial" -> "Arial",
    "helvetica" -> "Helvetica",
    "verdana" -> "Verdana",
    "courier" -> "Courier",
    "pacifico" -> "Pacifico"
  )

  val canvas = new facades.fabricjs.Canvas("main_canvas")
  canvas.setHeight(878)
  canvas.setWidth(622)



  CanvasEventListeners.main()
  ButtonEventListeners.main()



  jQuery.apply(document).keydown { (e: KeyboardEvent) =>


    val LEFT_KEY = 37
    val TOP_KEY = 38
    val RIGHT_KEY = 39
    val BOTTOM_KEY = 40
    val DELETE_KEY = 46
    val A_KEY = 65
    val B_KEY = 66
    val C_KEY = 67
    val I_KEY = 73
    val S_KEY = 83
    val U_KEY = 85
    val V_KEY = 86
    val X_KEY = 88
    val Y_KEY = 89
    val Z_KEY = 90


//    e.preventDefault();

    if (canvas.getActiveGroup() != null) {
      e.keyCode match {
        case LEFT_KEY => canvas.getActiveGroup().left -= 4
        case TOP_KEY => canvas.getActiveGroup().top -= 4
        case RIGHT_KEY => canvas.getActiveGroup().left += 4
        case BOTTOM_KEY => canvas.getActiveGroup().top += 4
        case DELETE_KEY => {
          canvas.getActiveGroup().getObjects().foreach{ obj =>
            canvas.remove(obj)
          }
          canvas.discardActiveGroup()
        }
        case _ =>
      }
    }

    if (canvas.getActiveObject() != null) {
      e.keyCode match {
        case LEFT_KEY => canvas.getActiveObject().left -= 4
        case TOP_KEY => canvas.getActiveObject().top -= 4
        case RIGHT_KEY => canvas.getActiveObject().left += 4
        case BOTTOM_KEY => canvas.getActiveObject().top += 4
        case DELETE_KEY => {
          canvas.remove(canvas.getActiveObject())
          canvas.discardActiveObject()
        }
        case _ =>
      }
    }

    (e.ctrlKey,e.keyCode) match {
      case (true,A_KEY) => println("CTRL+A")
      case (true,B_KEY) => println("CTRL+B")
      case (true,C_KEY) => println("CTRL+C")
      case (true,I_KEY) => println("CTRL+I")
      case (true,S_KEY) => println("CTRL+S")
      case (true,U_KEY) => println("CTRL+U")
      case (true,V_KEY) => println("CTRL+V")
      case (true,X_KEY) => println("CTRL+X")
      case (true,Y_KEY) => println("CTRL+Y")
      case (true,Z_KEY) => println("CTRL+Z")
      case _ =>
    }

    canvas.renderAll();
  }


}