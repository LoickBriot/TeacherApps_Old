package client.teachers

import client.teachers.OperationPageJS.{canvas}
import facades.fabricjs
import facades.fabricjs.Group
import org.scalajs.dom.document
import org.scalajs.dom.raw.MouseEvent
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

/**
  * Created by loick on 27/08/17.
  */
object ButtonEventListeners extends JSApp {

  @JSExport
  def main(): Unit = {


  }


  var display_image_button =  jQuery("#console")
  display_image_button.click { (e0: MouseEvent) =>
    PDFUtils.createPDF()
  }



  var addTextButton =  jQuery("#addText")
  addTextButton.click { (e0: MouseEvent) =>
    val text = new facades.fabricjs.Textbox("Tap and Typeeee")
    text.left = 50
    text.top = 100
    text.set("editable",()=>"true")
    text.width = 400
    // text.fontSize = jQuery("#fontSizeForm").value().toString.toDouble
    text.set("fontSize",()=>document.getElementById("fontSizeForm").getAttribute("value"))
    text.set("fontFamily",()=>document.getElementById("font").getAttribute("value"))
    text.set("fontWeight",()=>document.getElementById("fontWeight").getAttribute("value"))
    text.fill = jQuery("#text-color").value.toString
    text.backgroundColor = jQuery("#text-bg-color").value.toString
    canvas.add(text)


  }


  var addLineButton =  jQuery("#addLine")
  addLineButton.click { (e0: MouseEvent) =>
    val line = new fabricjs.Line(js.Array("50", "100", "100", "100"))
    line.setStroke("black")
    line.left=170
    line.top=150
    canvas.add(line)
  }


  var addImageButton =  jQuery("#addImage")
  addImageButton.click { (e0: MouseEvent) =>
    fabricjs.Image.fromURL("http://fabricjs.com/assets/pug_small.jpg",  (myImg: fabricjs.Object) => {
      canvas.add(myImg);
    }
    )
  }



  var addGroupButton =  jQuery("#addGroup")
  addGroupButton.click { (e0: MouseEvent) =>

    val with_correction = true
    val list = js.Array[fabricjs.Object]()

    if (jQuery("#number1").value().toString.nonEmpty && jQuery("#number2").value().toString.nonEmpty) {
      val positions = OperationUtils.computeDigitPosition(jQuery("#number1").value().toString.toDouble, jQuery("#number2").value().toString.toDouble, 200, 750, 20)

      var first_line = true
      positions.foreach { case (digit, x, y) =>

        if (digit == "-----") {
          first_line = false
          val width = positions.groupBy(_._3).map(_._2).toSeq.map(_.size).max * 20
          val line = new fabricjs.Line(js.Array("50", "100", "100", "100"))
          line.setStroke("black")
          line.left = x - width + 20 - 5
          line.top = canvas.getHeight() - y + 12
          line.width = width
          //canvas.add(line)
          list.append(line)
        } else {
          val text = if (with_correction || first_line) {
            new facades.fabricjs.Textbox(digit)
          } else {
            new facades.fabricjs.Textbox("_")
          }

          if (with_correction || first_line) {
            text.height = 4
          }

          text.left = x
          text.top = canvas.getHeight() - y
          text.set("editable", () => "true")
          // text.width = 20
          // text.fontSize = jQuery("#fontSizeForm").value().toString.toDouble
          text.set("fontSize", () => document.getElementById("fontSizeForm").getAttribute("value"))
          text.set("fontFamily", () => document.getElementById("font").getAttribute("value"))
          text.set("fontWeight", () => document.getElementById("fontWeight").getAttribute("value"))
          text.fill = jQuery("#text-color").value.toString
          text.backgroundColor = jQuery("#text-bg-color").value.toString
          list.append(text)
          //canvas.add(text)
        }
      }
      // list.reverse.foreach{obj => obj.bringToFront()}
      val group = new Group(list.reverse)
      group.set("editable", () => "true")
      group.setCoords()
      canvas.add(group)
      //  canvas.setActiveGroup(group)
      canvas.renderAll()

    }
  }


  var createGroupButton =  jQuery("#createGroup")
  createGroupButton.click { (e0: MouseEvent) =>

    if(canvas.getActiveGroup()!=null){

      var activegroup = canvas.getActiveGroup();
      var objectsInGroup = activegroup.getObjects();

      activegroup.clone((newgroup: Group) => {
        canvas.discardActiveGroup();
        objectsInGroup.foreach{case myobj2: fabricjs.Object =>
          canvas.remove(myobj2)
        }
        canvas.add(newgroup);
        canvas.setActiveObject(newgroup)
      });
    }
  }


  var deleteGroupButton =  jQuery("#deleteGroup")
  deleteGroupButton.click { (e0: MouseEvent) =>

    var activeObject = canvas.getActiveObject();
    if(activeObject.get("type")=="group"){
      var items = activeObject.asInstanceOf[Group].getObjects();
      activeObject._restoreObjectsState();
      canvas.remove(activeObject);
      items.foreach{ item =>
        canvas.add(item);
        //canvas.item(canvas.size()-1).hasControls = true;
      }
      val group = new Group(items)
      canvas.setActiveGroup(group)
      canvas.renderAll();
    }
  }





  def loop(obj1 : fabricjs.Object): Unit ={

    obj1.get("type") match {
      case "group" => {
        obj1.asInstanceOf[Group]._restoreObjectsState()
        obj1.asInstanceOf[Group].getObjects("group").foreach { obj =>
          loop(obj)
        }

        val array = js.Array[fabricjs.Object]()
        obj1.asInstanceOf[Group].getObjects().foreach { obj =>
          canvas.remove(obj)
          array.append(obj)
        }
        canvas.remove(obj1)
        canvas.add(new Group(array))
      }
      case _ => { }
    }
    canvas.renderAll()
  }


  //var displayAllButton =  jQuery("#displayAll")
  jQuery("#console").click{ (e: MouseEvent) =>
    canvas.getObjects().foreach{ obj =>
      loop(obj)
    }
    jQuery().delay(1000)
    canvas.getObjects().foreach{ obj =>
      loop(obj)
    }
    canvas.renderAll()
  }

  //var displayAllButton =  jQuery("#displayAll")
  jQuery("#formbutton").click{ (e: MouseEvent) =>
    canvas.getObjects().foreach{ obj =>
      loop(obj)
    }
    canvas.renderAll()
  }


  jQuery("#formbutton2").click{ (e: MouseEvent) =>
    canvas.getObjects().foreach{ obj =>
      loop(obj)
    }
    canvas.renderAll()
  }

}
