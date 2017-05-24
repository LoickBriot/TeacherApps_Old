package services.ajax

import org.scalajs.dom
import upickle.Js
import upickle.default._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

/**
  * Created by loick on 6/15/16.
  */
case class AjaxClientHandler(rootURL : String) extends autowire.Client[Js.Value, Reader, Writer] {
  override def doCall(req: Request): Future[Js.Value] = {
    dom.ext.Ajax.post(
      url = rootURL + req.path.mkString("/"),
      data = upickle.json.write(Js.Obj(req.args.toSeq: _*)),
      headers = Map("Content-Type" -> "application/json")
    ).map(_.responseText)
      .map(upickle.json.read)
  }

  def read[Result: Reader](p: Js.Value) = readJs[Result](p)
  def write[Result: Writer](r: Result) = writeJs(r)
}


