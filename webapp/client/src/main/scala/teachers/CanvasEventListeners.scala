package client.teachers

import client.teachers.OperationPageJS._
import facades.fabricjs
import facades.fabricjs.Textbox
import org.scalajs.dom.document
import org.scalajs.jquery.jQuery

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





  canvas.on("selection:created", ()=>{
    if(canvas.getActiveGroup()!=null){

      canvas.getObjects().map(e => s"${e.left},${e.top}").foreach(e => println(s"selected group : ${e}"))
    }
  })


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
        myobj.set("fontSize",()=>fontsize)
      }, null)
    } else {
      canvas.getActiveObject().asInstanceOf[Textbox].set("fontSize",()=>fontsize)
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
