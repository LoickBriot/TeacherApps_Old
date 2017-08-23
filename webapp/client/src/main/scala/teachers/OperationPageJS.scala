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


  canvas.on("selection:created", ()=>{
    if(canvas.getActiveGroup()!=null){

      println("group selected")
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




  var group_list = ListBuffer[Group]()
  var createGroupButton =  jQuery("#createGroup")
  createGroupButton.click { (e0: MouseEvent) =>
    if(canvas.getActiveGroup()!=null) {
      val activeGroup = canvas.getActiveGroup()

      group_list.append(activeGroup)
      //println("SIZE  " +group_list.size)
      //group_list.map(_.size()).foreach(e =>println("size " + e))
      //group_list.foreach(println)
    }
  }


  var deleteGroupButton =  jQuery("#deleteGroup")
  deleteGroupButton.click { (e0: MouseEvent) =>
    if(canvas.getActiveGroup()!=null) {
      val activeGroup = canvas.getActiveGroup()
      group_list = group_list.filter{group =>
        var flag = true
        activeGroup.forEachObject({ (myobj2: fabricjs.Object) =>
          if(group.contains(myobj2)){
            flag=false
          }
        },null)
        flag
      }
    } else if(canvas.getActiveObject()!=null) {
      val activeObject = canvas.getActiveObject()
      group_list = group_list.filter { group =>
        !group.contains(activeObject)
      }
    }
  }




  var addGroupButton =  jQuery("#addGroup")
  addGroupButton.click { (e0: MouseEvent) =>


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


    val text1 = new facades.fabricjs.Textbox("Tap and Typeeee")
    text1.left = 50
    text1.top = 200
    text1.set("editable",()=>"true")
    text1.width = 400
    // text1.fontSize = jQuery("#fontSizeForm").value().toString.toDouble
    text1.set("fontSize",()=>document.getElementById("fontSizeForm").getAttribute("value"))
    text1.set("fontFamily",()=>document.getElementById("font").getAttribute("value"))
    text1.set("fontWeight",()=>document.getElementById("fontWeight").getAttribute("value"))
    text1.fill = jQuery("#text-color").value.toString
    text1.backgroundColor = jQuery("#text-bg-color").value.toString


    val text2 = new facades.fabricjs.Textbox("Tap and Typeeee")
    text2.left = 50
    text2.top = 150
    text2.set("editable",()=>"true")
    text2.width = 400
    // text2.fontSize = jQuery("#fontSizeForm").value().toString.toDouble
    text2.set("fontSize",()=>document.getElementById("fontSizeForm").getAttribute("value"))
    text2.set("fontFamily",()=>document.getElementById("font").getAttribute("value"))
    text2.set("fontWeight",()=>document.getElementById("fontWeight").getAttribute("value"))
    text2.fill = jQuery("#text-color").value.toString
    text2.backgroundColor = jQuery("#text-bg-color").value.toString


    val group = new Group(js.Array(text,text1,text2))

    group.setCoords()
    canvas.add(text)
    canvas.add(text1)
    canvas.add(text2)
   // canvas.add(group)
    canvas.setActiveGroup(group)
    canvas.renderAll()
    group_list.append(group)
  }





// canvas.clear()
jQuery(document).click { (e0: MouseEvent) =>

  /*try {
    println("here")
    val groups_to_handle = ListBuffer[Group]()

    group_list.foreach { group =>
      group.forEachObject({ (myobj2: fabricjs.Object) =>
        if (canvas.getActiveObject() == myobj2 || (canvas.getActiveGroup()!=null && canvas.getActiveGroup().contains(myobj2)) ) {
          println("it happens !")
          groups_to_handle.append(group)
        }
      }, null)
    }


    if (groups_to_handle.nonEmpty) {
      println("group")
     // val new_group = empty_group
      //new_group.setLeft(canvas.getActiveObject().left)
      //new_group.setTop(canvas.getActiveObject().top)
    //  canvas.setActiveGroup(groups_to_handle.head)
//canvas.getActiveGroup().setOnGroup()
//      canvas.getActiveGroup().bringToFront()
  //    canvas.renderAll()

      val new_group = groups_to_handle.head

      canvas.getActiveGroup().forEachObject({ (myobj2: fabricjs.Object) =>
       // new_group.removeWithUpdate(myobj2)
        if (!new_group.contains(myobj2)) {
          new_group.add(myobj2)
          myobj2.setOnGroup()
          myobj2.bringToFront()
          canvas.bringToFront(myobj2)
        }
      },null)

     // val objects = ListBuffer[fabricjs.Object]()

      var i = 0
      //groups_to_handle.map(_.size()).foreach(println)
      groups_to_handle.foreach{ group =>
        println("group1 "+group.size())
        group.forEachObject({ (myobj2: fabricjs.Object) =>

         if (!canvas.getActiveGroup().contains(myobj2)){
            println("group2")
          //objects.append(myobj2)
           new_group.add(myobj2)
           myobj2.setOnGroup()
           myobj2.bringToFront()
            canvas.bringToFront(myobj2)
            i+=1
            println(i)
          }
        }, null)
      }

      canvas.deactivateAll()
      //canvas.add(new_group)
      new_group.setCoords()
      canvas.setActiveGroup(new_group).renderAll()


      //println(s"SIZE ${objects.size}")

      println(s"size ${canvas.getActiveGroup().size()}")
      //canvas.deactivateAll()
     // canvas.setActiveGroup(new_group)
      //throw new Exception()
    }
  } catch {
    case e : Throwable =>
  }*/

  /* if( (canvas.getActiveObject()!=null || canvas.getActiveGroup()!=null)) {
     val groups_to_handle = ListBuffer[Group]()

     group_list.foreach { group =>
       group.forEachObject({ (myobj2: fabricjs.Object) =>
         if ( (canvas.getActiveObject()!=null && canvas.getActiveObject() == myobj2) || (canvas.getActiveGroup() != null && canvas.getActiveGroup().contains(myobj2))) {
           println("it happens !")
           groups_to_handle.append(group)
         }
       }, null)
     }

     var initial_objects = if(canvas.getActiveObject()!=null){
       Seq(canvas.getActiveObject())
     } else {
       Nil
     }

     if(canvas.getActiveGroup()!=null) {
       try {
         canvas.getActiveGroup().forEachObject({ (myobj2: fabricjs.Object) =>
           initial_objects :+= myobj2
         }, null)
       } catch {
         case e : Throwable =>
       }
     }

     groups_to_handle.foreach{ group =>
       group.forEachObject({ (myobj2: fabricjs.Object) =>
         initial_objects :+= myobj2
       }, null)
     }


     var objs = canvas.getObjects().map { el =>
         el.set("active", () => true)
     }

     var group = new fabricjs.Group(objs);

     canvas.getObjects().foreach{ el =>
       if( !initial_objects.contains(el)) {
           group.removeWithUpdate(el)
         el.set("active", () => false)
       }
     }


     group.originX = "left"
     group.originY = "top"

     group.setCoords()
     canvas.setActiveGroup(group)//.renderAll();
   }*/





  if(canvas.getActiveGroup()!=null) {

  val groups_to_handle = ListBuffer[Group]()

  group_list.foreach { group =>
  group.forEachObject({ (myobj2: fabricjs.Object) =>
  if ( (canvas.getActiveObject()!=null && canvas.getActiveObject() == myobj2) || (canvas.getActiveGroup() != null && canvas.getActiveGroup().contains(myobj2))) {
  // println("it happens !")
  groups_to_handle.append(group)
}
}, null)
}

  if(canvas.getActiveGroup()!=null){
  groups_to_handle.foreach { group =>
  group.forEachObject({ (myobj2: fabricjs.Object) =>
  if(!canvas.getActiveGroup().contains(myobj2)) {
  myobj2.set("active", () => true)
  myobj2.bringToFront()
  canvas.getActiveGroup().addWithUpdate(myobj2)
}
}, null)
}
}else if(groups_to_handle.nonEmpty){
  canvas.discardActiveObject()
  canvas.discardActiveGroup()
  canvas.setActiveGroup(groups_to_handle.head)
  canvas.getActiveGroup().forEachObject({ (myobj2: fabricjs.Object) =>
  myobj2.set("active", () => true)
  myobj2.bringToFront()
},null)
}
  //canvas.discardActiveObject()
  //val group = new Group(js.Object())
  //group.add(activeObject)
  //canvas.setActiveGroup(group)
  //group.setCoords()
  // println(s"SIZE ${canvas.getActiveGroup().size()}")
}




}

  /*
  if(canvas.getActiveGroup()==null && canvas.getActiveObject()!=null && group_list.nonEmpty) {
      }
   */
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
  canvas.forEachObject({ (text : fabricjs.Textbox) =>


  smallTextBoxes.append(new SmallTextBox(
  text.getText().toString,
  text.getLeft().toInt,
  text.getTop().toInt,
  text.getWidth().toInt,
  text.getHeight().toInt,
  text.getFontSize().toInt,
  text.getFontFamily().toString,
  text.getTextBackgroundColor().toString,
  text.getTextAlign().toString,
  if(document.getElementById("fontWeight").getAttribute("value").isEmpty) "" else "bold",
  text.getFontStyle().toString,
  text.getTextDecoration().toString
  ))
}, null)

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