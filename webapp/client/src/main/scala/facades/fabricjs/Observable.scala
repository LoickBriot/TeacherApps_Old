package facades.fabricjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * 
  */
@js.native
@JSName("fabric.Observable")
class Observable() extends js.Object {
}

/**
  * 
  */
@js.native
@JSName("fabric.Observable")
object Observable extends js.Object {
  /**
    * Stops event observing for a particular event handler. Calling this method without arguments removes all handlers for all events
    */
  def off(eventName: String, handler: js.Function): Unit = js.native
  /**
    * Observes specified event
    */
  def on(eventName: String, handler: js.Function): Unit = js.native
  /**
    * Fires event with an optional options object
    */
  def trigger(eventName: String, options: js.Object = new js.Object()): Unit = js.native
}