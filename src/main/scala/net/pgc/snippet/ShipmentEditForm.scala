package net.pgc.snippet

import net.liftweb._
import http._
import common._
import util.Helpers._
import js._
import bootstrap.liftweb.ShipmentInfo
import net.pgc.model.{Company, Product, ShipmentLine, Shipment}
import net.liftweb.mapper.{By, ByList}
import net.liftweb.http.SHtml._
import net.liftweb.mapper.ByList
import net.liftweb.common.Full
import bootstrap.liftweb.ShipmentInfo
import scala.xml.NodeSeq


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
                  "#lines *" #> lines.map {
                    line => {
                      "#product *" #> (products.find(p => p.id == line.product.is.toInt).getOrElse(new Product())).name &
                        "#price *" #> line.price.toString &
                        "#qty *" #> line.quantity.toString &
                        "#value *" #> line.value.toString &
                        "type=submit" #> ajaxSubmit("Usuń linię", () => {
                          ShipmentLine.findAll(By(ShipmentLine.id, line.id.get)).head.delete_!
                          S.redirectTo("/edit/shipment/" + asInt(shipInfo.shipmentId).openOr(0))
                        })
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


  var qty = ""
  val products = Product.findAll()
  var product = products.head.id.get.toString

  def productSelect(in: NodeSeq) = ajaxSelect(products.map(i => (i.id.toString, i.name.toString)),
  Full("Podaj produkt"), {
    product =_
  })

  def addLine = {


    def processLine(): JsCmd = {
      val shipmentLine = new ShipmentLine
      val shipmentId = asInt(shipInfo.shipmentId).openOr(0)
      val shipment = Shipment.findAll(By(Shipment.id, shipmentId))
      val productDB = Product.findAll(By(Product.id,product.toLong)).head

      shipmentLine.quantity(asInt(qty).openOr(0)) //TODO: Zamiast defaultowego zera powinna być walidacja
      shipmentLine.price(productDB.price.get)
      shipmentLine.shipment(shipment.head)
      shipmentLine.product(product.toLong) //TODO: Mała walidacja potrzebna ;))
      shipmentLine.save()
      S.redirectTo("/edit/shipment/" + asInt(shipInfo.shipmentId).openOr(0))
    }

    "#qty" #> ajaxText(qty, qty = _) &
      "#product" #> productSelect _ &
      "type=submit" #> ajaxSubmit("Dodaj linię", processLine)

  }
}


//"#editlink [href]" #> ("edit/shipment/" + shipment.id.is.toString)