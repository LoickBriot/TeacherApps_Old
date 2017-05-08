package test

import java.io.File

import org.apache.pdfbox.pdmodel.font.{PDType0Font, PDType1Font}
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import org.apache.pdfbox.pdmodel.{PDResources, PDPageContentStream, PDPage, PDDocument}

/**
  * Created by loick on 13/04/17.
  */
object MultiplicationPDFTest {


  def main(args: Array[String]) {


    val font_size = 20
    val list = MultiplicationTest.computeDigitPosition(123.7,11.1, 200, 700, font_size)

    //Loading an existing document
    val document = new PDDocument()

    val docCatalog = document.getDocumentCatalog();
    val acroForm = new PDAcroForm(document)

    val res : PDResources = new PDResources();

    //val fontStream = getClass().getResourceAsStream("LiberationSans-Regular.ttf");
    val font = PDType0Font.load(document, new File("/home/loick/Pacifico.ttf"))
    val fontName = res.add(font)
    acroForm.setDefaultResources(res);

    println(fontName)
    //Creating a PDF Document
    val page = new PDPage()
    val contentStream = new PDPageContentStream(document, page);

    list.foreach{ el =>
      el._1 match {
        case "-----" => {
          val width = list.groupBy(_._3).map(_._2).toSeq.map(_.size).max*font_size
          contentStream.addRect(el._2-width+font_size-5,el._3, width+5, 1)
          contentStream.fill();
        }
        case _ => {
          contentStream.beginText();
          contentStream.newLineAtOffset(el._2, el._3);
          contentStream.setFont(font, font_size);
          contentStream.setLeading(14.5f);
          contentStream.showText(el._1);
          contentStream.endText();
        }

      }


    }


    //Setting the position for the line




    contentStream.close()

    document.addPage(page)
    //Saving the document
    document.save(new File("/home/loick/multiplication.pdf"));

    //Closing the document
    document.close();
  }
}
