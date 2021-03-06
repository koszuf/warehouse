package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import js.jquery.JQueryArtifacts
import sitemap._
import Loc._
import mapper._

import net.pgc.model._
import net.liftmodules.JQueryModule


case class ShipmentInfo(shipmentId: String)

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {

    val loggedIn = If(() => User.loggedIn_?, () => RedirectResponse("/user_mgt/login"))

    // where to search snippet
    LiftRules.addToPackages("net.pgc")

    //inicjalizacja DB
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor =
        new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
          Props.get("db.url") openOr
            "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
          Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    //Utworzenie struktury bazy
    Schemifier.schemify(true, Schemifier.infoF _, Product, Shipment, ShipmentLine, Company, User)

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index" >> User.AddUserMenusAfter >> LocGroup("main"),
      Menu.i("Wydania") / "wydania" >> LocGroup("work") >> loggedIn,
      Menu.i("Wydaj") / "wydaj" >> LocGroup("work") >> loggedIn,
      Menu.param[ShipmentInfo]("Edit Shipment", "Edit Shipment", s => Full(ShipmentInfo(s)),
        dinfo => dinfo.shipmentId) / "edit" / "shipment" >> LocGroup("work") >> loggedIn) :::
      Product.menus ::: Company.menus //tego się na razie nie da ukryć - trudno, koniec końców i tak wyleci jak zrezygnujemy z CRUDify

    def sitemapMutators = User.sitemapMutator
    LiftRules.setSiteMap(sitemapMutators(SiteMap(entries: _*)))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery172
    JQueryModule.init()

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    //    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Add H2Console
    if (Props.devMode || Props.testMode) {
      LiftRules.liftRequest.append({
        case r if (r.path.partPath match {
          case "console" :: _ => true
          case _ => false
        }
          ) => false
      })
    }

    //Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }
}
