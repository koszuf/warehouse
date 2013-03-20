package net.pgc.model

import net.liftweb.mapper._
import java.math.MathContext

/**
 * User: rakoczy
 * Date: 12.03.13
 * Time: 11:24
 */
class Product extends LongKeyedMapper[Product] with IdPK {

  def getSingleton = Product

  /**
   * Nazwa produktu    NOT NULL
   * Cena produktu     Number 0
   * Kod kreskowy      String
   * Kategoria produktu  String - docelowo może być to foreign key
   */
  object name extends MappedString(this, 100)

  object price extends MappedDecimal(this, MathContext.DECIMAL64, 2)

  object barcode extends MappedString(this, 20)

  object category extends MappedString(this, 40)

}

object Product extends Product with LongKeyedMetaMapper[Product] with CRUDify[Long, Product]


