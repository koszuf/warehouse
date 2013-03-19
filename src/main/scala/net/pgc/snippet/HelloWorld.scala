package net.pgc.snippet

import net.liftweb.util._
import Helpers._

class HelloWorld extends Logger {
  info("Test")
  def howdy = "#time *" #> Helpers.now.toString()
}

