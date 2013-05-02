package net.pgc.model

import net.liftweb.mapper.{MappedTextarea, MetaMegaProtoUser, MegaProtoUser}
import net.liftweb.common.Full
import net.liftweb.sitemap.Loc.LocGroup

/**
 * User: rakoczy
 * Date: 23.04.13
 * Time: 14:32
 */
class User extends MegaProtoUser[User] {

  def getSingleton = User

  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows = 10

    override def textareaCols = 50

    override def displayName = "Personal Essey"
  }

}

object User extends User with MetaMegaProtoUser[User] {
  override def screenWrap =
    Full(<lift:surround with="default" at="content">
      <lift:bind/>
    </lift:surround>)

  override def fieldOrder = List(id, firstName, lastName, email, locale, timezone, password, textArea)

  override def skipEmailValidation = true

  override def globalUserLocParams = LocGroup("main") :: super.globalUserLocParams


}
