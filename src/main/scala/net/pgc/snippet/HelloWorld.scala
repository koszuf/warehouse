package net.pgc.snippet

import net.liftweb.util._
import Helpers._
import net.liftweb.common.Logger

class HelloWorld extends Logger {
  info("Test")
  def howdy = "#time *" #> Helpers.now.toString()
}

