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


  val shipmnentsDB = Shipment.findAll(OrderBy(Shipment.id, Ascending))
  val lines = ShipmentLine.findAll(ByList(ShipmentLine.shipment, shipmnentsDB.map(_.id.is)))
  val shipments = for (h <- shipmnentsDB) yield (h, ShipmentLine.findAll(By(ShipmentLine.shipment, (h.id.is))))
  //TODO:Nie szukamy w bazie ale w lines
  val products = Product.findAll(ByList(Product.id, lines.map(_.product.is)))


  def render = {
    "* *" #> shipments.map {
      case (shipment, lines) =>
        ".company *" #> shipment.company.toString & ".lines *" #> lines.map {
          line => ".product *" #> products(line.product.is.toInt - 1).name & ".price *" #> line.price.toString & ".qty *" #> line.quantity.toString
        }
    }
  }
}