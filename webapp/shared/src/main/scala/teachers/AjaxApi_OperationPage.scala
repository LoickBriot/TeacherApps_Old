package teachers

import derive.key


/**
  * Created by loick on 5/13/16.
  */

@key("SmallTextBox") case class SmallTextBox(
                                              var text: String,
                                              var x: Int,
                                              var y: Int,
                                              var width: Int,
                                              var height: Int,
                                              var fontsize: Int,
                                              var font: String,
                                              var fontcolor: String,
                                              var textalign: String,
                                              var fontweight: String,
                                              var fontstyle: String,
                                              var textdecoration: String
                                            )

trait AjaxApi_OperationPage{


   def createPDF(boxList : Seq[SmallTextBox]): Unit



}