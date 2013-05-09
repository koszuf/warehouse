package net.pgc.snippet

import net.pgc.model._
import net.pgc.comet._
import net.pgc.lib._
import net.liftweb._
import http._
import util.Helpers._
import js._
import JsCmds._

object AddRandom {
  def render = "* *+" #> SHtml.ajaxButton("Add Random", () => {
    TheCart.get.contents.set(TheCart.get.contents.get :+
                             CartItem(Item.randomItem.asInstanceOf[Item], 1))
    Noop
  })
}
