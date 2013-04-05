package net.pgc.snippet

import net.liftweb._
import http._
import common._
import util.Helpers._
import js._
import bootstrap.liftweb.ShipmentInfo
import net.pgc.model.{Company, Product, ShipmentLine, Shipment}
import net.liftweb.mapper.{By, ByList}


/**
 * User: rakoczy
 * Date: 04.04.13
 * Time: 13:38
 */
class ShipmentEditForm(shipInfo: ShipmentInfo) extends Logger {

  def issueError(msg: String) = info(msg + shipInfo.shipmentId)

  def render = {
    asInt(shipInfo.shipmentId) match {
      case Full(idAsInt) => {
        Shipment.find(idAsInt) match {
          case Full(shipment) => {
            val lines = ShipmentLine.findAll(By(ShipmentLine.shipment, (shipment.id.is)))
            val company = Company.find(By(Company.id, (shipment.company.is))).openOr(new Company())
            ".company *" #> company.name & ".when *" #> shipment.when.toString &
              ".who *" #> shipment.who.toString & ".whom *" #> shipment.whom.toString &
              (lines match {
                case List() => ".lines_table" #> "Brak pozycji zlecenia"
                case _ => {
                  val products = Product.findAll(ByList(Product.id, lines.map(_.product.is)))
                  ".lines *" #> lines.map {
                    line => {
                      ".product *" #> (products.find(p => p.id == line.product.is.toInt).getOrElse(new Product())).name &
                        ".price *" #> line.price.toString &
                        ".qty *" #> line.quantity.toString &
                        ".value *" #> line.value.toString
                    }
                  }
                }
              })
          }
          case _ => {
            issueError("Brak zlecenia: ")
            "*" #> "Nie ma takiego zlecenia"
          }
        }
      }
      case _ => {
        issueError("Błędny format: ")
        "*" #> "Nieprawidłowy format zlecenia"

      }
    }
  }
}
