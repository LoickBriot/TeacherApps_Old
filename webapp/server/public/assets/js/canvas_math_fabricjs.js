    fabric.Object.prototype.transparentCorners = false;
    fabric.Object.prototype.padding = 5;
    
     
  var $ = function(id){return document.getElementById(id)};


  var canvas = this.__canvas = new fabric.Canvas('c');
  	canvas.setHeight(878);
	canvas.setWidth(622);
	

var canvas_history = [];
var index = 0;


function SmallTextBox (text, x, y, width, height, fontsize,font,fontcolor, textalign,fontweight, fontstyle, textdecoration  ) {
    this.text= text,
    this.x= x,
    this.y=y,
    this.width=width,
    this.height=height,
    this.fontsize=fontsize,
    this.font= font,
    this.fontcolor=fontcolor,
    this.textalign=textalign,
    this.fontweight=fontweight,
    this.fontstyle=fontstyle,
    this.textdecoration=textdecoration
}


function addText() { 
var text = new fabric.Textbox('Tap and Type', { 
      left: 50,
      top: 100,
      width:400,
      fontFamily: 'arial',
      fill: '#333',
      fontSize: 28,
})






canvas.add(text);
 canvas_history.push( [canvas._objects[canvas._objects.length-1], true] );
  index = canvas_history.length-1;
  console.log("added -- " + canvas_history.length + " index: " +index);
}


function addLine() {
    // alert("Line");
    canvas.add(new fabric.Line([50, 100, 100, 100], {
        left: 170,
        top: 150,
        stroke: 'black'
    }));
}
    
function addImageFromURL(url) {
fabric.Image.fromURL(url, function(myImg) {
 canvas.add(myImg); 
});
}

   document.getElementById('text-color').onchange = function() {
var color = this.value;
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    text.setFill(color);
  });
} else {
   canvas.getActiveObject().setFill(this.value);
}
            canvas.renderAll();
        };


		
		document.getElementById('text-bg-color').onchange = function() {
var color = this.value;
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){
    text.setBackgroundColor(color);
  });
} else {
   canvas.getActiveObject().setBackgroundColor(this.value);
}           
            canvas.renderAll();
        };
		
			
	
	 function changeFont(font) {
console.log(font);
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    text.setFontFamily(font);
  });
} else {
   canvas.getActiveObject().setFontFamily(font);
}
            canvas.renderAll();
        };
		
 function changeFontSize(fontsize) {
	
console.log(fontsize);
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    text.setFontSize(fontsize);
  });
} else {
   canvas.getActiveObject().setFontSize(fontsize);
}
            canvas.renderAll();

        };

		
	

 function changeTextAlign(align) {
	
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    text.setTextAlign(align);
  });
} else {
   canvas.getActiveObject().setTextAlign(align);
}
            canvas.renderAll();

        };


 function changeFontWeight() {
	
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    if(text.get("fontWeight")=="bold"){
   text.set("fontWeight", "");
}else{
text.set("fontWeight", "bold");
}
  });
} else {
if(canvas.getActiveObject().get("fontWeight")=="bold"){
   canvas.getActiveObject().set("fontWeight", "");
}else{
canvas.getActiveObject().set("fontWeight", "bold");
}
}
            canvas.renderAll();

        };

 function changeFontStyle() {
	
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    if(text.get("fontStyle")=="italic"){
   text.set("fontStyle", "");
}else{
text.set("fontStyle", "italic");
}
  });
} else {
if(canvas.getActiveObject().get("fontStyle")=="italic"){
   canvas.getActiveObject().set("fontStyle", "");
}else{
canvas.getActiveObject().set("fontStyle", "italic");
}
}
            canvas.renderAll();

        };

 function changeTextDecoration() {
	
if(canvas.getActiveGroup()){
  canvas.getActiveGroup().forEachObject(function(text){ 
    if(text.get("textDecoration")=="underline"){
   text.set("textDecoration", "");
}else{
text.set("textDecoration", "underline");
}
  });
} else {
if(canvas.getActiveObject().get("textDecoration")=="underline"){
   canvas.getActiveObject().set("textDecoration", "");
}else{
canvas.getActiveObject().set("textDecoration", "underline");
}
}
            canvas.renderAll();

        };
		


 /*
http://stackoverflow.com/questions/19043219/undo-redo-feature-in-fabric-js
http://stackoverflow.com/questions/18998429/undo-and-redo-command-in-canvas-using-fabric-js
https://codepen.io/keerotic/pen/yYXeaR
 var history = {
    redo_list: [],
    undo_list: [],
    saveState: function(canvas, list, keep_redo) {
      keep_redo = keep_redo || false;
      if(!keep_redo) {
        this.redo_list = [];
      }
      
      (list || this.undo_list).push(canvas.toDataURL());   
    },
    undo: function(canvas, ctx) {
      this.restoreState(canvas, ctx, this.undo_list, this.redo_list);
    },
    redo: function(canvas, ctx) {
      this.restoreState(canvas, ctx, this.redo_list, this.undo_list);
    },
    restoreState: function(canvas, ctx,  pop, push) {
      if(pop.length) {
        this.saveState(canvas, push, true);
        var restore_state = pop.pop();
        var img = new Element('img', {'src':restore_state});
        img.onload = function() {
          ctx.clearRect(0, 0, 600, 400);
          ctx.drawImage(img, 0, 0, 600, 400, 0, 0, 600, 400);  
        }
      }
    }
  }
  

 
   $('undo').addEvent('click', function() {
    history.undo(canvas, ctx);
  });
  
  $('redo').addEvent('click', function() {
    history.redo(canvas, ctx);
  });
*/

  






