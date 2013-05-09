package net.pgc.snippet

import net.pgc.model._
import net.liftweb._
import http._
import util._
import Helpers._
import sitemap._
import Loc._

object SearchPage {
  def menu = Menu.i("Search") / "search" >>
  If(() => S.param("q").isDefined, "Need Search Term") >>
  Snippet("Items", render)

  def render = {
    "tbody *" #> AllItemsPage.
    renderItems(Item.search(S.param("q") openOr "*****^^^^"))
  }
}

