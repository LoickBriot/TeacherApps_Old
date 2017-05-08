package util

import java.time.Duration

object Util {
  def formatDuration(duration: Duration): String = {
    val s = duration.getSeconds.toInt
    s"${s / 3600}h ${(s % 3600) / 60}m ${s % 60}s"
  }
}
