package shared

import java.time.ZonedDateTime



case class WelcomeResponse(var id: Int, var name: String, var states: Seq[StateMap])
case class StateMap(var state: String, var count: Int)

trait AjaxApi_WelcomePage {

 // def getProjectsTasksStates(elements: String, dateFilter: String): Seq[WelcomeResponse]

}
