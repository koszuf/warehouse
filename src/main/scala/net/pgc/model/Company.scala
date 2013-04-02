package net.pgc.model

import net.liftweb.mapper._

/**
 * User: rakoczy
 * Date: 02.04.13
 * Time: 13:04
*/
class Company extends LongKeyedMapper [Company] with IdPK{

  def getSingleton = Company

  /**
   * name
   * notes
   *
   */
  object name extends MappedString(this,100)

  object notes extends MappedTextarea(this,1000)



}

object Company extends Company with LongKeyedMetaMapper[Company] with CRUDify[Long,Company]
