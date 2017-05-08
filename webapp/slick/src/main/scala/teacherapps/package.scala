import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.{Cron, CronType}
import com.cronutils.parser.CronParser

package object slick {



  object ClassLevels extends Enumeration {
    val CP, CE1, CE2, CM1, CM2 = Value
  }

}
