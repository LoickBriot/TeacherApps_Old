package teachers

import derive.key


/**
  * Created by loick on 5/13/16.
  */

@key("JSONTextBox") case class JSONTextBox(
                                              text: String,
                                              x: Int,
                                              y: Int,
                                              width: Int,
                                              height: Int,
                                              fontsize: Int,
                                              font: String,
                                              fontcolor: String,
                                              textalign: String,
                                              fontweight: String,
                                              fontstyle: String,
                                              textdecoration: String
                                            )


@key("JSONLine") case class JSONLine(
                                              x: Int,
                                              y: Int,
                                              width: Int,
                                              height: Int
                                            )

trait AjaxApi_OperationPage{


   def createPDF(correction : Boolean, boxList : Seq[JSONTextBox], lines : Seq[JSONLine]): Unit



}