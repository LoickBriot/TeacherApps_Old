package client.teachers

import client.teachers.OperationPageJS._
import facades.fabricjs
import facades.fabricjs.Textbox
import org.scalajs.dom.{document, _}
import org.scalajs.dom.raw.MouseEvent
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

/**
  * Created by loick on 27/08/17.
  */
object CanvasEventListeners extends JSApp {

  @JSExport
  def main(): Unit = {


  }




  var currentFont = "arial"
  var currentFontSize = "20"
  var currentFontWeight = ""
  var currentFontStyle = ""
  var currentFontColor = "#000000"
  var currentBackgroundColor = "#FFFFFF"
  var currentTextAlign = "left"
  var currentTextDecoration = ""



  var rectangle = null
  var isDown = false
  var isTextActivated = false
  var origX = 0.0d
  var origY = 0.0d
  var origX2 = 0.0d
  var origY2 = 0.0d

  class Pointer(var x:js.Any, var y:js.Any) extends js.Object
  jQuery(document).mousedown( (e0: MouseEvent) => {
    jQuery().delay(1000)
    if(isTextActivated) {
      if (isDown) {
         isDown = false
      } else {
        isDown = true

        origX =e0.clientX - document.getElementById("main_canvas").getBoundingClientRect().left
        origY = e0.clientY - document.getElementById("main_canvas").getBoundingClientRect().top
       // println(s"${e0.clientX - document.getElementById("main_canvas").getBoundingClientRect().left}   ---   ${e0.clientY - document.getElementById("main_canvas").getBoundingClientRect().top}")

      }
    }
  }, null)


  jQuery(document).mouseup( (e0: MouseEvent) => {
    jQuery().delay(1000)
    if(isTextActivated) {
      if (isDown) {
       // println(s" ${e0.clientX - document.getElementById("main_canvas").getBoundingClientRect().left}   ---   ${e0.clientY - document.getElementById("main_canvas").getBoundingClientRect().top}")
         isDown = false
        origX2 = e0.clientX - document.getElementById("main_canvas").getBoundingClientRect().left
        origY2 = e0.clientY - document.getElementById("main_canvas").getBoundingClientRect().top

        val text = new facades.fabricjs.Textbox("...")
        //text.lockScalingY = false
        text.left = roundCoordinates(Math.min(origX,origX2))
        text.top = roundCoordinates(Math.min(origY,origY2))
        text.set("editable",()=>"true")
        text.width = Math.abs(origX2-origX)
        text.height = Math.abs(origY2-origY)
        text.set("fontSize",()=>document.getElementById("fontSizeForm").getAttribute("value"))
        text.set("fontFamily",()=>document.getElementById("font").getAttribute("value"))
        text.set("fontWeight",()=>document.getElementById("fontWeight").getAttribute("value"))
        text.fill = jQuery("#text-color").value.toString
        text.backgroundColor = jQuery("#text-bg-color").value.toString
        canvas.add(text)
        isTextActivated = false
      }
    }
  }, null)


 /* canvas.on("mouse:down", (e0: MouseEvent) => {
    isDown = true;
    origX = (e0.clientX - document.getElementById("main_canvas").getBoundingClientRect().left)
    origY = (e0.clientY - document.getElementById("main_canvas").getBoundingClientRect().top)

    println(s"${origX}  ${origY}")
  });

  canvas.on("mouse:up", (e0: MouseEvent) => {
    isDown = false;
    origX2 = (e0.clientX - document.getElementById("main_canvas").getBoundingClientRect().left)
    origY2 = (e0.clientY - document.getElementById("main_canvas").getBoundingClientRect().top)

    println(s"${origX2}  ${origY2}")
  });*/




  canvas.on("before:selection:cleared", ()=>{

    /* Font */
    document.getElementById("font").setAttribute("value",currentFont)
    document.getElementById("font").textContent=fontMap.getOrElse(currentFont,"")


    /* FontSize */
    document.getElementById("fontSizeForm").setAttribute("value",currentFontSize.toString)
    document.getElementById("fontSizeForm").textContent=currentFontSize.toString


    /* FontWeight */
    if(currentFontWeight=="bold") {
      document.getElementById("fontWeight").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
      document.getElementById("fontWeight").setAttribute("value", "bold")
    } else {
      document.getElementById("fontWeight").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
      document.getElementById("fontWeight").setAttribute("value", "")
    }


    /* FontStyle */
    if(currentFontStyle=="italic") {
      document.getElementById("fontStyle").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
      document.getElementById("fontStyle").setAttribute("value", "italic")
    } else {
      document.getElementById("fontStyle").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
      document.getElementById("fontStyle").setAttribute("value", "")
    }


    /* TextDecoration */
    if(currentFontStyle=="underline") {
      document.getElementById("textDecoration").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
      document.getElementById("textDecoration").setAttribute("value", "underline")
    } else {
      document.getElementById("textDecoration").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
      document.getElementById("textDecoration").setAttribute("value", "")

      /* TextAlign */
      currentTextAlign match {
        case "left" => {
          document.getElementById("textAlignLeft").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "left")
        }
        case "center" => {
          document.getElementById("textAlignCenter").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "center")
        }
        case "right" => {
          document.getElementById("textAlignRight").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "right")
        }
        case "justify" => {
          document.getElementById("textAlignJustify").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "justify")
        }
      }


      /* FontColor */
      document.getElementById("text-color").setAttribute("value", currentFontColor)


      /* BackgroundColor */
      document.getElementById("text-bg-color").setAttribute("value", currentBackgroundColor)
    }

  })





  /*canvas.on("selection:created", ()=>{
    if(canvas.getActiveGroup()!=null){

      canvas.getObjects().map(e => s"${e.left},${e.top}").foreach(e => println(s"selected group : ${e}"))
    }
  })*/


  canvas.on("object:selected", ()=>{

    if(canvas.getActiveGroup()==null){
      val activeObject = canvas.getActiveObject().asInstanceOf[Textbox]

      /* Font */
      document.getElementById("font").setAttribute("value",activeObject.get("fontFamily").toString)
      document.getElementById("font").textContent=fontMap.getOrElse(activeObject.get("fontFamily").toString,"")


      /* FontSize */
      document.getElementById("fontSizeForm").setAttribute("value",activeObject.get("fontSize").toString)
      document.getElementById("fontSizeForm").textContent=activeObject.get("fontSize").toString



      /* FontWeight */
      if(activeObject.get("fontWeight")=="bold") {
        document.getElementById("fontWeight").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
        document.getElementById("fontWeight").setAttribute("value", "bold")
      } else {
        document.getElementById("fontWeight").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
        document.getElementById("fontWeight").setAttribute("value", "")
      }


      /* FontStyle */
      if(activeObject.get("fontStyle")=="italic") {
        document.getElementById("fontStyle").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
        document.getElementById("fontStyle").setAttribute("value", "italic")
      } else {
        document.getElementById("fontStyle").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
        document.getElementById("fontStyle").setAttribute("value", "")
      }


      /* TextDecoration */
      if(activeObject.get("textDecoration")=="underline") {
        document.getElementById("textDecoration").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
        document.getElementById("textDecoration").setAttribute("value", "underline")
      } else {
        document.getElementById("textDecoration").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
        document.getElementById("textDecoration").setAttribute("value", "")
      }


      document.getElementById("textAlignLeft").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
      document.getElementById("textAlignCenter").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
      document.getElementById("textAlignRight").setAttribute("style", "cursor: pointer;filter:brightness(100%);")
      document.getElementById("textAlignJustify").setAttribute("style", "cursor: pointer;filter:brightness(100%);")


      /* TextAlign */
      activeObject.get("textAlign") match {
        case "left" => {
          document.getElementById("textAlignLeft").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "left")
        }
        case "center" => {
          document.getElementById("textAlignCenter").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "center")
        }
        case "right" => {
          document.getElementById("textAlignRight").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "right")
        }
        case "justify" => {
          document.getElementById("textAlignJustify").setAttribute("style", "cursor: pointer;filter:brightness(85%);")
          document.getElementById("textAlignForm").setAttribute("value", "justify")
        }
      }


      /* FontColor */
      document.getElementById("text-color").setAttribute("value", activeObject.get("fill").toString)


      /* BackgroundColor */
      document.getElementById("text-bg-color").setAttribute("value", activeObject.get("backgroundColor").toString)
    }


  }
  )




  jQuery("#text-color").change { () =>
    val color = jQuery("#text-color").value.toString;
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Object) =>
        myobj.set("fill",()=>color)
      }, null)

    } else {
      canvas.getActiveObject().set("fill",()=>color)
    }
    canvas.renderAll();
  }



  jQuery("#text-bg-color").change{ () =>
    val color = jQuery("#text-bg-color").value.toString;
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Object) =>
        myobj.set("backgroundColor",()=>color)
      }, null)
    } else {
      canvas.getActiveObject().set("backgroundColor",()=>color)
    }
    canvas.renderAll();
  }


  @JSExport
  def changeFont(font:String) {

    document.getElementById("font").setAttribute("value",font)
    document.getElementById("font").textContent=fontMap.getOrElse(font,"")

    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Textbox) =>
        myobj.set("fontFamily",()=>font)
      }, null)
    } else {
      canvas.getActiveObject().asInstanceOf[Textbox].set("fontFamily",()=>font)
    }
    canvas.renderAll();
  };


  @JSExport
  def changeFontSize(fontsize:Int) {
    document.getElementById("fontSizeForm").setAttribute("value",fontsize.toString)
    document.getElementById("fontSizeForm").textContent=fontsize.toString
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Textbox) =>
        myobj.set("fontSize",()=>fontsize.toString)
      }, null)
    } else {
      canvas.getActiveObject().asInstanceOf[Textbox].set("fontSize",()=>fontsize.toString)
    }
    canvas.renderAll();
  };


  @JSExport
  def changeTextAlign(align:String) {
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Textbox) =>
        myobj.set("textAlign",()=>align)
      }, null)
    } else {
      canvas.getActiveObject().asInstanceOf[Textbox].set("textAlign",()=>align)
    }

    canvas.renderAll()

  };




  @JSExport
  def changeFontWeight() {
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Textbox) =>
        if(myobj.get("fontWeight")=="bold"){
          myobj.set("fontWeight",()=>"")
          document.getElementById("fontWeight").setAttribute("style","cursor: pointer;filter:brightness(100%);")
          document.getElementById("fontWeight").setAttribute("value","")

        }else{
          myobj.set("fontWeight",()=>"bold")
          document.getElementById("fontWeight").setAttribute("style","cursor: pointer;filter:brightness(85%);")
          document.getElementById("fontWeight").setAttribute("value","bold")

        }
      }, null)
    } else {
      if(canvas.getActiveObject().asInstanceOf[Textbox].get("fontWeight")=="bold"){
        canvas.getActiveObject().asInstanceOf[Textbox].set("fontWeight",()=>"")
        document.getElementById("fontWeight").setAttribute("style","cursor: pointer;filter:brightness(100%);")
        document.getElementById("fontWeight").setAttribute("value","")

      }else{
        canvas.getActiveObject().asInstanceOf[Textbox].set("fontWeight",()=>"bold")
        document.getElementById("fontWeight").setAttribute("style","cursor: pointer;filter:brightness(85%);")
        document.getElementById("fontWeight").setAttribute("value","bold")
      }
    }
    //Thread sleep 100

    canvas.renderAll();
  };



  @JSExport
  def changeFontStyle() {
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Textbox) =>
        if(myobj.fontStyle=="italic"){
          myobj.set("fontStyle",()=>"")
        }else{
          myobj.set("fontStyle",()=>"italic")
        }
      }, null)
    } else {

      if( canvas.getActiveObject().asInstanceOf[Textbox].fontStyle=="italic"){
        canvas.getActiveObject().asInstanceOf[Textbox].set("fontStyle",()=>"")
      }else{
        canvas.getActiveObject().asInstanceOf[Textbox].set("fontStyle",()=>"italic")
      }
    }
    canvas.renderAll();
  }



  @JSExport
  def changeTextDecoration() {
    if(canvas.getActiveGroup()!=null){
      canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Textbox) =>
        if(myobj.textDecoration=="underline"){
          myobj.set("textDecoration",()=>"")
        }else{
          myobj.set("textDecoration",()=>"underline")
        }
      }, null)
    } else {
      if( canvas.getActiveObject().asInstanceOf[Textbox].textDecoration=="underline"){
        canvas.getActiveObject().asInstanceOf[Textbox].set("textDecoration",()=>"")
      }else{
        canvas.getActiveObject().asInstanceOf[Textbox].set("textDecoration",()=>"underline")
      }
    }
    canvas.renderAll();
  }



}
