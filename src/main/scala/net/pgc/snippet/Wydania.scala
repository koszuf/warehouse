package net.pgc.snippet

import net.liftweb.common.Logger
import net.liftweb.util._
import Helpers._
import net.pgc.model._
import net.liftweb.mapper.{ByList, Ascending, OrderBy}

/**
 * User: rakoczy
 * Date: 22.03.13
 * Time: 14:02
 */
class Wydania extends Logger {

  def page = Shipment.findAll(OrderBy(Shipment.id, Ascending))

  val linieMap = ShipmentLine.findAll(ByList(ShipmentLine.id, page.map(_.id.is)))
  info(linieMap)
  val linie = Map.empty ++ linieMap.map(l => (l.id.is, l))

  def render = "#row *" #> page.map(wydanie =>
    "#kiedy *" #> wydanie.when &
      "#nr *" #> wydanie.id &
      "#komu *" #> wydanie.company &
      "#ile *" #> linie(wydanie.id.get).quantity

  //TODO:Jak zrobić zagnieżdżone????
  //check it on    https://groups.google.com/forum/#!forum/scalanetpl
  )

}
