package facades.fabricjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * Triangle class
  */
@js.native
@JSName("fabric.Triangle")
class Triangle(options: js.Object = new js.Object()) extends Object(options) {
  /**
    * Returns SVG representation of an instance
    */
  def toSVG(reviver: js.Function = null): String = js.native
}

/**
  * Triangle class
  */
@js.native
@JSName("fabric.Triangle")
object Triangle extends js.Object {
  /**
    * Returns fabric.Triangle instance from an object representation
    */
  def fromObject(`object`: js.Object): js.Object = js.native
}