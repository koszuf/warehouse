package net.pgc.snippet

import net.liftweb.common.Logger
import net.liftweb.util._
import Helpers._
import net.pgc.model._
import net.liftweb.mapper.{By, ByList, Ascending, OrderBy}

/**
 * User: rakoczy
 * Date: 22.03.13
 * Time: 14:02
 */
class Shipments extends Logger {


  val shipmentsDB = Shipment.findAll(OrderBy(Shipment.id, Ascending))
  val linesDB = ShipmentLine.findAll(ByList(ShipmentLine.shipment, shipmentsDB.map(_.id.is)))
  val companys = Company.findAll(ByList(Company.id, shipmentsDB.map(_.company.is)))
  def render = ".shipments *" #> shipmentsDB.map(shipment =>
    "#company *" #> (companys.find(c=> c.id==shipment.company.is.toInt).getOrElse(new Company())).name &
      "#when *" #> shipment.when.toString &
      "#who *" #> shipment.who.toString & "#whom *" #> shipment.whom.toString &
      "#editlink [href]" #> ("edit/shipment/" + shipment.id.is.toString)
  )

  /*
  Na kiedyś
  def render = {
    "* *" #> shipments.map {
      case (shipment, existingLines) =>
        ".company *" #> companys(shipment.company.is.toInt -1 ).name & ".when *" #> shipment.when.toString &
          ".who *" #> shipment.who.toString & ".whom *" #> shipment.whom.toString &   //dodaj linka
          (existingLines match {
            case List() => ".lines_table *" #> ""
            case lines => ".lines *" #> lines.map {
              line => ".product *" #> products(line.product.is.toInt - 1).name & ".price *" #> line.price.toString &
                ".qty *" #> line.quantity.toString & ".value *" #> line.value.toString
            }
          })
    }
  }*/
}