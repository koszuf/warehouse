package net.pgc.snippet

import net.liftweb.util._
import  Helpers._
import java.text.NumberFormat
import xml.Text
import net.liftweb.http.DispatchSnippet

/**
 * User: rakoczy
 * Date: 04.04.13
 * Time: 09:10
 */
object RuntimeStats extends DispatchSnippet{

  @volatile
  var totalMem = Runtime.getRuntime.totalMemory
  @volatile
  var freeMem = Runtime.getRuntime.freeMemory

  @volatile
  var sessions = 1

  @volatile
  var lastUpdate = now

  val startedAt = now

  private def nf(in: Long): String = NumberFormat.getInstance.format(in)

  def dispatch = {
    case "total_mem" => i => Text(nf(totalMem))
    case "free_mem" => i => Text(nf(freeMem))
    case "sessions" => i => Text(sessions.toString)
    case "updated_at" => i => Text(lastUpdate.toString)
    case "started_at" => i => Text(startedAt.toString)
  }

}
