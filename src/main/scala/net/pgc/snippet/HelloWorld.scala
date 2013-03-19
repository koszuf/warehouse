package net.pgc.snippet

import net.liftweb.util._
import Helpers._

class HelloWorld {
  def howdy = "#time *" #> Helpers.now.toString()
}

