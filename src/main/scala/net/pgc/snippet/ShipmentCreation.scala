package net.pgc.snippet


import net.liftweb.util.Helpers._
import net.liftweb.common._
import net.liftweb.http.{S, RequestVar}
import net.liftweb.http.SHtml._
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.PassThru
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js._
import scala.xml.Text
import net.liftweb.http.js.jquery.JqJsCmds._


/**
 * User: rakoczy
 * Date: 03.04.13
 * Time: 14:17
 */
class ShipmentCreation {
  var company = ""
  var who = ""

  def render = {

    def process(): JsCmd = {
      SetHtml("result", Text(company + " oraz " + who))
    }


    "@company" #> ajaxText(company, company = _) &
    "@who" #> ajaxText(who, who = _) &
      "type=submit" #> ajaxSubmit("Click Me", process)
    //TODO: 1. Walidacja, 2.Pobieranie danych z tabeli company, 3. formant kalendarz
  }


}
