package client.teachers
/**
  * Created by lbriot on 10/17/16.
  */

import facades.fabricjs
import facades.fabricjs.{Group, Textbox}
import org.scalajs.dom.raw.MouseEvent
import services.ajax.AjaxClientHandler
import org.scalajs.dom.{KeyboardEvent, document}
import teachers.{AjaxApi_OperationPage, SmallTextBox}

import scala.scalajs.js
import scala.scalajs.js.JSApp
import autowire._
import org.scalajs.jquery.jQuery

import scala.scalajs.js.annotation.JSExport
import scalajs.concurrent.JSExecutionContext.Implicits.queue
import upickle.default._

import scala.collection.mutable.ListBuffer
import scala.util.Try



object OperationPageJS extends JSApp {



  var ajaxClientHandler = new AjaxClientHandler("/operation/ajaxapi/")[AjaxApi_OperationPage]


  var display_image_button =  jQuery("#console")
  display_image_button.click { (e0: MouseEvent) =>
    println("HERE 999")
    createPDF()
  }



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


  canvas.on("selection:cleared", ()=>{
    canvas.getObjects().map(e => s"${e.left},${e.top}").foreach(e => println(s"cleared group : ${e}"))
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
    val positions = computeDigitPosition(123.7,11.1, 200, 600, 20)

    var first_line = true
    positions.foreach { case (digit, x, y) =>

      if (digit == "-----") {
        first_line = false
        val width = positions.groupBy(_._3).map(_._2).toSeq.map(_.size).max*20
        val line = new fabricjs.Line(js.Array("50", "100", "100", "100"))
        line.setStroke("black")
        line.left=x-width+20-5
        line.top=canvas.getHeight()-y+12
        line.width=width
        //canvas.add(line)
        list.append(line)
      } else {
        val text = if(with_correction || first_line ){
          new facades.fabricjs.Textbox(digit)
        } else {
          new facades.fabricjs.Textbox("_")
        }

        if(with_correction || first_line ){
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


  def getMultiplicationSteps(x:Double, y:Double) : Seq[String] = {

    var steps = Seq[String]()
    var i = 1
    println(s"  ${x}")
    println(s"* ${y}")
    println("------------")
    val point_index = x.toString.split("\\.").last.size + y.toString.split("\\.").last.size

    y.toString.filter(_.isDigit).reverse.foreach{ digit =>
      val step = x.toString.filter(_.isDigit).toInt*digit.toString.toInt*i

      val strstep = (0 until Math.max((Math.min(x.toString.filter(_.isDigit).length,y.toString.filter(_.isDigit).length)-step.toString.length),0)).map(e=>"0").mkString("")+step.toString

      steps :+= strstep.slice(0, strstep.size-point_index).mkString("")+"."+strstep.slice(strstep.size-point_index,strstep.size).mkString("")
      println(s"+ ${strstep.slice(0, strstep.size-point_index+1).mkString("")+"."+strstep.slice(strstep.size-point_index,strstep.size).mkString("")}")
      i*=10
    }
    println("------------")
    println(s"  ${x*y}")

    return steps
  }

  def computeDigitPosition(x:Double,y:Double, initial_xoffset:Int, initial_yoffset:Int, font_size:Int) : Seq[(String,Int,Int)] = {

    var list = Seq[(String,Int,Int)]()

    var current_x_offset = 0
    var current_y_offset = 0

    x.toString.reverse.foreach{ digit =>
      list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
      current_x_offset+=font_size
    }

    current_x_offset=0
    current_y_offset+=font_size

    y.toString.reverse.foreach{ digit =>
      list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
      current_x_offset+=font_size
    }

    list :+= ("x", initial_xoffset-Math.max(x.toString.length, y.toString.length)*font_size, initial_yoffset-current_y_offset)


    list :+= ("-----", initial_xoffset, initial_yoffset-current_y_offset-12)
    current_x_offset=0
    current_y_offset+=font_size+10

    getMultiplicationSteps(x,y).foreach{ step =>

      step.toString.reverse.foreach{ digit =>
        list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
        current_x_offset+=font_size
      }

      current_x_offset=0
      current_y_offset+=font_size
    }

    list :+= ("-----", initial_xoffset, initial_yoffset-current_y_offset+8)

    current_y_offset+=10

    (x*y).toString.reverse.foreach{ digit =>
      list :+= (digit.toString, initial_xoffset-current_x_offset, initial_yoffset-current_y_offset)
      current_x_offset+=font_size
    }

    return list
  }






  jQuery.apply(document).keydown{ (e: KeyboardEvent) =>


    if(e.keyCode==37){
      e.preventDefault();
      if(canvas.getActiveGroup()!=null) {
        val activeGroup = canvas.getActiveGroup()
        // activeGroup.left = activeGroup.left - 4;
        //println(canvas.getActiveGroup().size())

        canvas.getActiveGroup().left = canvas.getActiveGroup().left - 4;


      } else {
        val activeGroup = canvas.getActiveObject()
        activeGroup.left = activeGroup.left - 4;
      }
      canvas.renderAll();
    }
    if(e.keyCode==38){
      e.preventDefault();
      if(canvas.getActiveGroup()!=null) {
        val activeGroup = canvas.getActiveGroup()
        /*canvas.getActiveGroup().forEachObject({ (myobj2: fabricjs.Object) =>
          myobj2.top = myobj2.top -4
          myobj2.bringToFront()
        },null)*/
        activeGroup.top = activeGroup.top - 4;

      } else {
        val activeGroup = canvas.getActiveObject()
        activeGroup.top = activeGroup.top - 4;
      }
      canvas.renderAll();

    }
    if(e.keyCode==39){
      e.preventDefault();
      if(canvas.getActiveGroup()!=null) {
        val activeGroup = canvas.getActiveGroup()
        /*canvas.getActiveGroup().forEachObject({ (myobj2: fabricjs.Object) =>
          myobj2.left = myobj2.left +4
          myobj2.bringToFront()
        },null)*/
        activeGroup.left = activeGroup.left + 4;

      } else {
        val activeGroup = canvas.getActiveObject()
        activeGroup.left = activeGroup.left + 4;
      }
      canvas.renderAll();

    }
    if(e.keyCode==40){
      e.preventDefault();
      if(canvas.getActiveGroup()!=null) {
        val activeGroup = canvas.getActiveGroup()
        /*canvas.getActiveGroup().forEachObject({ (myobj2: fabricjs.Object) =>
          myobj2.top = myobj2.top +4
          myobj2.bringToFront()
        },null)*/
        activeGroup.top = activeGroup.top + 4;

      } else {
        val activeGroup = canvas.getActiveObject()
        activeGroup.top = activeGroup.top + 4;
      }
      canvas.renderAll();
    }
    if(e.keyCode==46){
      if(canvas.getActiveGroup()!=null) {
        canvas.getActiveGroup().forEachObject({ (myobj : fabricjs.Object) =>
          myobj.remove()
        }, null)
      } else {
        canvas.getActiveObject().remove();
      }
      canvas.discardActiveGroup().renderAll();
    }

    canvas.renderAll();
  }


  /*

   */



  @JSExport
  def main(): Unit = {


  }

  @JSExport
  def createPDF(): Unit = {
    /* val json = document.getElementById("canvas_data").getAttribute("value")
     //println(json)


     println(json)
     val boxList = if(json.isEmpty) Nil else read[Seq[SmallTextBox]](json)
     println(boxList)*/
    val smallTextBoxes = ListBuffer[SmallTextBox]()


    canvas.getObjects("textbox").foreach{ text =>
      //if (Try{text.asInstanceOf[Textbox].getText();true}.getOrElse(false)) {
      smallTextBoxes.append(new SmallTextBox(
        text.asInstanceOf[Textbox].getText().toString,
        text.asInstanceOf[Textbox].getLeft().toInt,
        text.asInstanceOf[Textbox].getTop().toInt,
        text.asInstanceOf[Textbox].getWidth().toInt,
        text.asInstanceOf[Textbox].getHeight().toInt,
        text.asInstanceOf[Textbox].getFontSize().toInt,
        text.asInstanceOf[Textbox].getFontFamily().toString,
        text.asInstanceOf[Textbox].getTextBackgroundColor().toString,
        text.asInstanceOf[Textbox].getTextAlign().toString,
        if (document.getElementById("fontWeight").getAttribute("value").isEmpty) "" else "bold",
        text.asInstanceOf[Textbox].getFontStyle().toString,
        text.asInstanceOf[Textbox].getTextDecoration().toString
      ))
    }




    canvas.getObjects("group").foreach { case activeObject : Group =>
      if(activeObject.get("type")=="group"){
        var items = activeObject.asInstanceOf[Group].getObjects("textbox");
        activeObject._restoreObjectsState();

        //canvas.remove(activeObject);
        items.foreach{ text =>
          smallTextBoxes.append(new SmallTextBox(
            text.asInstanceOf[Textbox].getText().toString,
            text.asInstanceOf[Textbox].getLeft().toInt,
            text.asInstanceOf[Textbox].getTop().toInt,
            text.asInstanceOf[Textbox].getWidth().toInt,
            text.asInstanceOf[Textbox].getHeight().toInt,
            text.asInstanceOf[Textbox].getFontSize().toInt,
            text.asInstanceOf[Textbox].getFontFamily().toString,
            text.asInstanceOf[Textbox].getTextBackgroundColor().toString,
            text.asInstanceOf[Textbox].getTextAlign().toString,
            if (document.getElementById("fontWeight").getAttribute("value").isEmpty) "" else "bold",
            text.asInstanceOf[Textbox].getFontStyle().toString,
            text.asInstanceOf[Textbox].getTextDecoration().toString
          ))
          //canvas.add(item);
          //canvas.item(canvas.size()-1).hasControls = true;
        }
       // val group = new Group(items)
        //canvas.setActiveGroup(group)
        //canvas.renderAll();
      }
    }

    println(smallTextBoxes)
    ajaxClientHandler.createPDF(smallTextBoxes).call().onSuccess{ case result =>

      println("PDF generated !")
    }
  }



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