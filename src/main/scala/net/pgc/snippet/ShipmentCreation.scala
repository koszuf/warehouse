package net.pgc.snippet


import net.liftweb.util.Helpers._
import net.liftweb.common._
import net.liftweb.http.{S, RequestVar}
import net.liftweb.http.SHtml._
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.PassThru
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js._
import xml.{NodeSeq, Text}
import net.liftweb.http.js.jquery.JqJsCmds._

import _root_.net.liftweb.http._
import net.liftweb.common._

import net.liftweb.util.Helpers._

import js._
import js.jquery._
import JqJsCmds._
import JsCmds._
import SHtml._
import _root_.scala.xml.{Text, NodeSeq}
import net.pgc.model._
//import net.liftmodules.


/**
 * User: rakoczy
 * Date: 03.04.13
 * Time: 14:17
 */
class ShipmentCreation extends StatefulSnippet {


  def dispatch = {
    case _ => render
  }

  val companies = Company.findAll()
  var company = companies.head.id.get.toString
  var who = ""
  var whom = ""

  def companySelect(in: NodeSeq) = ajaxSelect(companies.map(i => (i.id.toString, i.name.toString)),
  Full("Nazwa firmy"), {
    c => company = c
  })



  def render = {

    def process(): JsCmd = {
      //SetHtml("result", Text("Nr firmy:"+company + ", wydał:" + who))
      val shipment = new Shipment()
      shipment.company(company.toInt)
      shipment.who(who)
      shipment.when(now)
      shipment.whom(whom)
      shipment.save()

      S.redirectTo("/edit/shipment/" + shipment.id.toString)
    }

    "#who" #> ajaxText(who, who = _) &
      "#whom" #> ajaxText(whom, whom = _) &
      "#company" #> companySelect _ &
      "type=submit" #> ajaxSubmit("Utwórz wydanie", process)
    //TODO: Walidacja - czy wprowadzony został 'wydający' oraz 'odbierający'
  }



}
