package test

import java.io.{File, FileOutputStream, IOException}
import java.math.BigInteger
;

import com.microsoft.schemas.vml.CTGroup
import derive.key
import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import org.apache.pdfbox.text.{TextPosition, PDFTextStripper}
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.openxmlformats.schemas.wordprocessingml.x2006.main.{CTPicture}
;
import upickle.default._

/**
  * Created by loick on 12/04/17.
  */

@key("TextPDF") case class TextPDF(val text: String, x: Int, y: Int, width:Int, height:Int, size: Float, font:String)
object Word2PDF {


  val stripper = new PDFTextStripper() {


    override protected def writeString( text: String, textPositions: java.util.List[TextPosition]) : Unit  = {

      val firstProsition = textPositions.get(0);
      val lastPosition = textPositions.get(textPositions.size()-1);
      println(s"${text}   ${firstProsition.getX}  ${firstProsition.getXScale}  ${firstProsition.getEndX}  ${firstProsition.getXDirAdj}   ${firstProsition.getHeight}")
      println(s"${text}   ${lastPosition.getX}  ${lastPosition.getXScale}  ${lastPosition.getEndX}  ${lastPosition.getXDirAdj}   ${lastPosition.getHeight}")

      val textpdf = new TextPDF(text, firstProsition.getXDirAdj().toInt, firstProsition.getYDirAdj().toInt, lastPosition.getXDirAdj.toInt - firstProsition.getXDirAdj().toInt, (lastPosition.getHeight.toInt+1)*2,  firstProsition.getFontSize(), firstProsition.getFont().toString)
      super.writeString("new TextPDF");
      super.writeString(write(textpdf));

    }
    var startOfLine = true;
  };



  def main(args: Array[String]) {


    System.out.println("Document converted started");
    val doc = new XWPFDocument();
    val reader = PDDocument.load(new File("/home/loick/new.pdf"));
    val pdfStripper = new PDFTextStripper();

    val json = stripper.getText(reader).split("new TextPDF|\n").filter(_.nonEmpty)

    println(json.mkString(""))

    val list = json.map(read[TextPDF])


    for (i <- 0 until list.size) {
      val text = list(i)
      val p = doc.createParagraph();
      /*val run = p.createRun();
      run.setText(text.text);
      run.setFontSize(text.size.toInt)
      run.setFontFamily(text.font.replaceAll("PDType1Font ",""))
      run.addBreak(BreakType.PAGE);
*/

/*
     val ctGroup = CTGroup.Factory.newInstance();

      val ctShape = ctGroup.addNewShape();
      ctShape.setStyle(s"position:absolute;margin-left:${(text.x-90).toString}px;margin-top:${(text.y-72).toString}px;width:${text.width+50}px;height:${text.size*2}px");
      ctShape.setBorderbottomcolor("000000")
      val ctTxbxContent = ctShape.addNewTextbox().addNewTxbxContent();
      ctTxbxContent.addNewP().addNewR().addNewT().setStringValue(text.text);

      val ctGroupNode = ctGroup.getDomNode();

           val ctPicture = CTPicture.Factory.parse(ctGroupNode);
             val run2=p.createRun();
      val cTR = run2.getCTR();
      cTR.addNewPict();
      cTR.setPictArray(0, ctPicture);

        */
val ctGroup = CTGroup.Factory.newInstance();

      val ctShape = ctGroup.addNewShape();
      //ctShape.
      ctShape.setStyle(s"border='0pt solid #000000';position:absolute;margin-left:${(text.x-90).toString}px;margin-top:${(text.y-72).toString}px;width:${text.width+50}px;height:${text.size*2}px");
      val textBox = ctShape.addNewTextbox()

     //textBox.setStyle(s"position:absolute;margin-left:${(text.x-90).toString}px;margin-top:${(text.y-72).toString}px;width:${text.width+50}px;height:${text.size*2}px");
      val ctTxbxContent = textBox.addNewTxbxContent();
      //ctShape.addNewFill()
     // ctShape.addNewBorderbottom()

      val ctP = ctTxbxContent.addNewP();
      ctP.addNewR().addNewT().setStringValue("The TextFrame text...");
     /* val ctPPr = ctP.addNewPPr();
      val ctPBdr = ctPPr.addNewPBdr();
      var ctBorder = ctPBdr.addNewLeft(); ctBorder.setColor("000000"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
      ctBorder = ctPBdr.addNewRight(); ctBorder.setColor("FFFFFF"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
      ctBorder = ctPBdr.addNewTop(); ctBorder.setColor("FFFFFF"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
      ctBorder = ctPBdr.addNewBottom(); ctBorder.setColor("FFFFFF"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
*/
      val ctGroupNode = ctGroup.getDomNode();
      val ctPicture = CTPicture.Factory.parse(ctGroupNode);
      val run2=p.createRun();
      val cTR = run2.getCTR();
      cTR.addNewPict();
      cTR.setPictArray(0, ctPicture);

      /*val ctSdtContentBlock = doc.getDocument.getBody.addNewSdt().addNewSdtContent()
      val ctP = ctSdtContentBlock.addNewP();
      ctP.addNewR().addNewT().setStringValue("The TextFrame text...");
      val ctPPr = ctP.addNewPPr();

      val ctPBdr = ctPPr.addNewPBdr();
      var ctBorder = ctPBdr.addNewLeft(); ctBorder.setColor("000000"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
      ctBorder = ctPBdr.addNewRight(); ctBorder.setColor("FFFFFF"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
      ctBorder = ctPBdr.addNewTop(); ctBorder.setColor("FFFFFF"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));
      ctBorder = ctPBdr.addNewBottom(); ctBorder.setColor("FFFFFF"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));

      val paragraph = doc.createParagraph();
      val run=paragraph.createRun();
      run.setText("Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... ");
*/
      //val ctBorder = ctPBdr.addNewLeft(); ctBorder.setColor("000000"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));


    }
    val out = new FileOutputStream("/home/loick/newword.docx");
    doc.write(out);
    out.close();
    reader.close();
    System.out.println("Document converted successfully");
  }

}
