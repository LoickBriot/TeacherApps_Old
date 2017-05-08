package test

import java.io.File;
import java.io.FileOutputStream;
import com.microsoft.schemas.vml.CTGroup
import org.apache.poi.xwpf.usermodel._
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;


/**
  * Created by loick on 11/04/17.
  */
object WordTest {

  def main(args: Array[String]) {

    //Blank Document
    val document= new XWPFDocument();
    //Write the Document in file system
    val out = new FileOutputStream(new File("/home/loick/createparagraph.docx"));



    val paragraph = document.createParagraph();
    val run=paragraph.createRun()
    run.setText("The Body text: ")

    val table = document.createTable()

    val tableRowOne = table.getRow(0)
    tableRowOne.getCell(0).setText("col one, row one")
    /*val ctGroup = CTGroup.Factory.newInstance();

    val ctShape = ctGroup.addNewShape();
    ctShape.setStyle("position:absolute;mso-position-horizontal:center;margin-left:500pt;margin-top:400pt;width:100pt;height:24pt");
    val ctTxbxContent = ctShape.addNewTextbox().addNewTxbxContent();
    ctTxbxContent.addNewP().addNewR().addNewT().setStringValue("The TextBox text...\n Blablab la");
   val ctGroupNode = ctGroup.getDomNode();
    val ctPicture = CTPicture.Factory.parse(ctGroupNode);
    //val ctBorder = ctPBdr.addNewLeft(); ctBorder.setColor("000000"); ctBorder.setVal(STBorder.SINGLE); ctBorder.setSz(BigInteger.valueOf(4)); ctBorder.setSpace(BigInteger.valueOf(7));

    val run2=paragraph.createRun();
     val cTR = run2.getCTR();
     cTR.addNewPict();
     cTR.setPictArray(0, ctPicture);

    val paragraph3 = document.createParagraph();
    val run3=paragraph3.createRun();
    run3.setText("Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... Lorem ipsum semit color ... ");
*/
    /*
    //create Paragraph
    val paragraph = document.createParagraph();


    val paragraphOneRunTwo = paragraph.createRun();
    paragraphOneRunTwo.setText("Font Style two");
    paragraphOneRunTwo.setTextPosition(1000);

    val paragraphOneRunTwo2 = paragraph.createRun();
    paragraphOneRunTwo2.setText("Font Style two");
    paragraphOneRunTwo2.setTextPosition(1000);

    //Set bottom border to paragraph
    paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);

    //Set left border to paragraph
    paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);

    //Set right border to paragraph
    paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);

    //Set top border to paragraph
    paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);


    //Set Bold an Italic
    val paragraphOneRunOne = paragraph.createRun();
    paragraphOneRunOne.setBold(true);
    paragraphOneRunOne.setItalic(true);
    paragraphOneRunOne.setText("Font Style");
    paragraphOneRunOne.addBreak();

    //Set text Position


    //Set Strike through and Font Size and Subscript
    val paragraphOneRunThree = paragraph.createRun();
    paragraphOneRunThree.setStrike(true);
    paragraphOneRunThree.setFontSize(20);
    paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
    paragraphOneRunThree.setText(" Different Font Styles");
    //Set alignment paragraph to RIGHT
    paragraph.setAlignment(ParagraphAlignment.RIGHT);


    //create table
    val table = document.createTable();

    //create first row
    val tableRowOne = table.getRow(0);
    tableRowOne.getCell(0).setText("col one, row one");
    tableRowOne.addNewTableCell().setText("col two, row one");
    tableRowOne.addNewTableCell().setText("col three, row one");

    //create second row
    val tableRowTwo = table.createRow();
    tableRowTwo.getCell(0).setText("col one, row two");
    tableRowTwo.getCell(1).setText("col two, row two");
    tableRowTwo.getCell(2).setText("col three, row two");

    //create third row
    val tableRowThree = table.createRow();
    tableRowThree.getCell(0).setText("col one, row three");
    tableRowThree.getCell(1).setText("col two, row three");
    tableRowThree.getCell(2).setText("col three, row three");


    val run=paragraph.createRun();
    run.setText("""At tutorialspoint.com, we strive hard to
    provide quality tutorials for self-learning
    purpose in the domains of Academics, Information
    Technology, Management and Computer Programming Languages.""");


*/

    document.write(out);
    out.close();
    System.out.println("createparagraph.docx written successfully");



  }


}
