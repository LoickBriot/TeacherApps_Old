package test

import java.awt.Color
import java.io.File

import org.apache.pdfbox.pdmodel._
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject

/**
  * Created by loick on 11/04/17.
  */

/*
https://www.tutorialspoint.com/apache_poi_word/apache_poi_word_document.htm
https://www.tutorialspoint.com/pdfbox/pdfbox_removing_pages.htm
 */
object PDFTest {


  def readPDF(filepath: String): PDDocument = {
    val file = new File("C:/PdfBox_Examples/sample.pdf");
    val document = PDDocument.load(file);
    return document
  }

  def writePDF(document: PDDocument,filepath: String): Unit = {
    document.save(filepath)
  }




  def main(args: Array[String]) {



    //Loading an existing document
    val document = new PDDocument()

    //Creating a PDF Document
    val page = new PDPage()

    val contentStream = new PDPageContentStream(document, page);


    //Creating PDImageXObject object
   /* val pdImage = PDImageXObject.createFromFile("/home/loick/image.jpg",document);

    //Drawing the image in the PDF document
    contentStream.drawImage(pdImage, 70, 250);


    //Setting the non stroking color
    contentStream.setNonStrokingColor(Color.DARK_GRAY);

    //Drawing a rectangle
    contentStream.addRect(200, 650, 100, 100);

    //Drawing a rectangle
    contentStream.fill();
    */
    //Begin the Content stream
    contentStream.beginText();

    //Setting the font to the Content stream
    contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );

    //Setting the leading
    contentStream.setLeading(14.5f);

    //Setting the position for the line
    contentStream.newLineAtOffset(25, 725);

    val text1 = "This is an example of adding text to a page in the pdf document. We can add as many lines"
    contentStream.showText(text1);


    val text2 = "as we want like this using the ShowText()  method of the ContentStream class";

    //Adding text in the form of string

    contentStream.newLine();
    contentStream.showText(text2);
    //Ending the content stream
    contentStream.endText();



    System.out.println("Content added");



    contentStream.beginText();

    //Setting the font to the Content stream
    contentStream.setFont( PDType1Font.HELVETICA, 16 );

    //Setting the leading
    contentStream.setLeading(19.5f);

    //Setting the position for the line
    contentStream.newLineAtOffset(200, 500);

    val text3 = "Ihpqfoui azi roppi rzeoiu foizeu pez"



    val text4 = "eoiezu ri zpoi ezori apiz r$";

    //Adding text in the form of string
    contentStream.showText(text3);
    contentStream.newLine();
    contentStream.showText(text4);
    //Ending the content stream
    contentStream.endText();
    System.out.println("Content added");





    contentStream.close()

    document.addPage(page)
    //Saving the document
    document.save(new File("/home/loick/new.pdf"));

    //Closing the document
    document.close();



  }

}


/*
 //Creating PDF document object
PDDocument document = new PDDocument();

//Creating a blank page
PDPage blankPage = new PDPage();

//Adding the blank page to the document
document.addPage( blankPage );

//Creating the PDDocumentInformation object
PDDocumentInformation pdd = document.getDocumentInformation();

//Setting the author of the document
pdd.setAuthor("Tutorialspoint");

// Setting the title of the document
pdd.setTitle("Sample document");

//Setting the creator of the document
pdd.setCreator("PDF Examples");

//Setting the subject of the document
pdd.setSubject("Example document");

//Setting the created date of the document
Calendar date = new GregorianCalendar();
date.set(2015, 11, 5);
pdd.setCreationDate(date);
//Setting the modified date of the document
date.set(2016, 6, 5);
pdd.setModificationDate(date);

//Setting keywords for the document
pdd.setKeywords("sample, first example, my pdf");

//Saving the document
document.save("C:/PdfBox_Examples/doc_attributes.pdf");

System.out.println("Properties added successfully ");

//Closing the document
document.close();
 */


/*
  //Loading an existing PDF document
      File file = new File("C:/PdfBox_Examples/sample.pdf");
      PDDocument document = PDDocument.load(file);

      //Instantiating Splitter class
      Splitter splitter = new Splitter();

      //splitting the pages of a PDF document
      List<PDDocument> Pages = splitter.split(document);

      //Creating an iterator
      Iterator<PDDocument> iterator = Pages.listIterator();

      //Saving each page as an individual document
      int i = 1;
      while(iterator.hasNext()) {
      PDDocument pd = iterator.next();
      pd.save("C:/PdfBox_Examples/sample"+ i++ +".pdf");
      }
      System.out.println("Multiple PDF’s created");
      document.close();
 */


/*
 //Loading an existing PDF document
      File file1 = new File("C:/PdfBox_Examples/sample1.pdf");
      PDDocument doc1 = PDDocument.load(file1);

      File file2 = new File("C:/PdfBox_Examples/sample2.pdf");
      PDDocument doc2 = PDDocument.load(file2);

      //Instantiating PDFMergerUtility class
      PDFMergerUtility PDFmerger = new PDFMergerUtility();

      //Setting the destination file
      PDFmerger.setDestinationFileName("C:/PdfBox_Examples/merged.pdf");

      //adding the source files
      PDFmerger.addSource(file1);
      PDFmerger.addSource(file2);

      //Merging the two documents
      PDFmerger.mergeDocuments();

      System.out.println("Documents merged");
      //Closing the documents
      doc1.close();
      doc2.close();
 */