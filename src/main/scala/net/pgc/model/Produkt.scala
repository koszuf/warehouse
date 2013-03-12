package net.pgc.model

import net.liftweb.mapper._

/**
 * Created with IntelliJ IDEA.
 * User: rakoczy
 * Date: 12.03.13
 * Time: 11:24
 */
class Produkt extends LongKeyedMapper[Produkt] with IdPK {

  def getSingleton = Produkt

  /**
   * Nazwa produktu    NOT NULL
   * Cena produktu     Number 0
   * Kod kreskowy      String
   * Kategoria produktu  String - docelowo może być to foreign key
   */
  object nazwa extends MappedString(this, 100)

  object cena extends MappedDouble(this)

  object kodKreskowy extends MappedString(this, 20)

  object kategoria extends MappedString(this, 40)

}

object Produkt extends Produkt with LongKeyedMetaMapper[Produkt] with CRUDify[Long, Produkt]


