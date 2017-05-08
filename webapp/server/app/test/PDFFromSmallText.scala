package test

import java.io.File

import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import org.apache.pdfbox.pdmodel.{PDResources, PDPageContentStream, PDPage, PDDocument}
import teachers.SmallTextBox

/**
  * Created by loick on 20/04/17.
  */
object PDFFromSmallText {



  def main(args: Array[String]) {

    val boxList = Seq(
      new SmallTextBox(
        "Test",
        100,
        200,
        300,
        400,
        28,
        "pacifico",
        "#333",
        "left",
        "bold",
        "italic",
        "underline"
      )
    )

    createPDFPage(boxList)
  }

  def createPDFPage(boxList : Seq[SmallTextBox]): Unit = {

    //Loading an existing document
    val document = new PDDocument()

    val docCatalog = document.getDocumentCatalog();
    val acroForm = new PDAcroForm(document)

    val res : PDResources = new PDResources();

    //val fontStream = getClass().getResourceAsStream("LiberationSans-Regular.ttf");
    val font = PDType0Font.load(document, new File("/home/loick/Pacifico.ttf"))
    val fontName = res.add(font)
    acroForm.setDefaultResources(res);

    //Creating a PDF Document
    val page = new PDPage()


    val contentStream = new PDPageContentStream(document, page);

    boxList.foreach { box =>
      contentStream.beginText();
      //contentStream.newLineAtOffset(610,771);
      contentStream.newLineAtOffset((0.9576*box.x).toInt, (771-(0.9576*box.y).toInt));
      contentStream.setFont(font, box.fontsize);
      //contentStream.setLeading(14.5f);
      contentStream.showText(box.text);
      contentStream.endText();
    }

    contentStream.close()

    document.addPage(page)
    //Saving the document
    document.save(new File("/home/loick/smalltextbox.pdf"));

    //Closing the document
    document.close();


  }
}
