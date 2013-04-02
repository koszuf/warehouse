package net.pgc.model

import net.liftweb.mapper._
import net.liftweb.common.Logger
import java.math.MathContext

/**
 * User: rakoczy
 * Date: 20.03.13
 * Time: 14:56
 */
class ShipmentLine extends LongKeyedMapper[ShipmentLine] with IdPK with Logger {

  def getSingleton = ShipmentLine

  /**
   * Id shipmentu
   * Id produktu
   * Cena
   * Ilość
   */
  object shipment extends MappedLongForeignKey(this, Shipment)

  object product extends MappedLongForeignKey(this, Product)

  object price extends MappedDecimal(this, MathContext.DECIMAL64, 2)

  object quantity extends MappedInt(this)

  def value = price.get * quantity.get

}

object ShipmentLine extends ShipmentLine with LongKeyedMetaMapper[ShipmentLine]
