package net.pgc.model

import net.liftweb.mapper._
import net.liftweb.common.Logger

/**
 * User: rakoczy
 * Date: 20.03.13
 * Time: 09:00
 */
class Shipment extends LongKeyedMapper[Shipment] with IdPK with Logger {
  def getSingleton = Shipment

  /**
   * Czas wydania
   * Dla jakiej firmy
   * Kto odbierał (whom)
   * Kto wydawał (who)
   * Nr PZ-ki
   */

  object when extends MappedDateTime(this)
  object company extends MappedLongForeignKey(this,Company)


  object whom extends MappedString(this, 100)

  //TODO: Zmienić na foreign relationship to User
  object who extends MappedString(this, 100)

  //TODO: Zmienić na foreign relationship to deliveryNote
  object deliveryNote extends MappedString(this, 100)

}

object Shipment extends Shipment with LongKeyedMetaMapper[Shipment]