package net.pgc.model

import net.liftweb._
import util._
import Helpers._
import common._
import json._
import scala.xml.NodeSeq
import net.pgc.model._
import scala.xml.Node

/**
 * An item in inventory
 */
class Item extends Product

/**
 * The Item companion object
 */
object Item {
  private implicit val formats =
    net.liftweb.json.DefaultFormats + BigDecimalSerializer

  val getProducts = Product.findAll()
  //val productsls: Item  = getProducts.map{identity}(collection.breakOut)

  private var items: List[Product] = getProducts//parse(data).extract[List[Item]]

  private var listeners: List[Product => Unit] = Nil

  /**
   * Convert a JValue to an Item if possible
   */
  def apply(in: JValue): Box[Product] = Helpers.tryo{in.extract[Item]}

  /**
   * Extract a String (id) to an Item
   */
  def unapply(id: String): Option[Product] = Product.find(id)

  /**
   * Extract a JValue to an Item
   */
  def unapply(in: JValue): Option[Product] = apply(in)

  /**
   * The default unapply method for the case class.
   * We needed to replicate it here because we
   * have overloaded unapply methods
   */
  def unapply(in: Any): Option[(String, String,
                                BigDecimal, String,
                                String)] = {
    in match {
      case i: Item => Some((i.id.toString,
    		  				i.name,
                            i.price,
                            i.barcode,
                            i.category))
      case _ => None
    }
  }

  /**
   * Convert an item to XML
   */
  implicit def toXml(item: Product): Node =
    <item>{Xml.toXml(item)}</item>


  /**
   * Convert the item to JSON format.  This is
   * implicit and in the companion object, so
   * an Item can be returned easily from a JSON call
   */
  implicit def toJson(item: Product): JValue =
    Extraction.decompose(item)

  /**
   * Convert a Seq[Item] to JSON format.  This is
   * implicit and in the companion object, so
   * an Item can be returned easily from a JSON call
   */
  implicit def toJson(items: Seq[Product]): JValue =
    Extraction.decompose(items)

  /**
   * Convert a Seq[Item] to XML format.  This is
   * implicit and in the companion object, so
   * an Item can be returned easily from an XML REST call
   */
  implicit def toXml(items: Seq[Product]): Node =
    <items>{
      items.map(toXml)
    }</items>

  /**
   * Get all the items in inventory
   */
  def inventoryItems: Seq[Product] = items

  // The raw data
 def data =
"""[
  {"id": "1234", "name": "Cat Food",
  "price": 4.25,
  "barcode": "1000",
  "category": "4"
  },
  {"id": "1235", "name": "Dog Food",
  "price": 7.25,
  "barcode": "5000,"
  "category": "72"
  },
  {"id": "1236", "name": "Fish Food",
  "price": 2,
  "barcode": "200",
  "category": "45"
  },
  {"id": "1237", "name": "Sloth Food",
  "price": 18.33,
  "barcode": "750",
  "category": "62"
  },
]
"""

  /**
   * Select a random Item
   */
  def randomItem: Product = synchronized {
    items(Helpers.randomInt(items.length))
  }

  /**
   * Find an item by id
   */
  def find(id: String): Box[Product] = synchronized {
    items.find(_.id == id)
  }

  /**
   * Add an item to inventory
   */
  def add(item: Product): Product = {
    synchronized {
      items = item :: items.filterNot(_.id == item.id)
      updateListeners(item)
    }
  }

  /**
   * Find all the items with the string in their name or
   * description
   */
  def search(str: String): List[Product] = {
    val strLC = str.toLowerCase()

    items.filter(i =>
      i.name.toLowerCase.indexOf(strLC) >= 0 ||
                 i.name.toLowerCase.indexOf(strLC) >= 0)
  }

  /**
   * Deletes the item with id and returns the
   * deleted item or Empty if there's no match
   */
  def delete(id: String): Box[Product] = synchronized {
    var ret: Box[Product] = Empty

    val Id = id // an upper case stable ID for pattern matching

    items = items.filter {
      case i@Item(Id, _, _, _, _) =>
        ret = Full(i) // side effect
        false
      case _ => true
    }

    ret.map(updateListeners)
  }

  /**
   * Update listeners when the data changes
   */
  private def updateListeners(item: Product): Product = {
    synchronized {
      listeners.foreach(f =>
        Schedule.schedule(() => f(item), 0 seconds))

      listeners = Nil
    }
    item
  }

  /**
   * Add an onChange listener
   */
  def onChange(f: Product => Unit) {
    synchronized {
      // prepend the function to the list of listeners
      listeners ::= f
    }
  }

}

/**
 * A helper that will JSON serialize BigDecimal
 */
object BigDecimalSerializer extends Serializer[BigDecimal] {
  private val Class = classOf[BigDecimal]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BigDecimal] = {
    case (TypeInfo(Class, _), json) => json match {
      case JInt(iv) => BigDecimal(iv)
      case JDouble(dv) => BigDecimal(dv)
      case value => throw new MappingException("Can't convert " + value + " to " + Class)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case d: BigDecimal => JDouble(d.doubleValue)
  }
}
