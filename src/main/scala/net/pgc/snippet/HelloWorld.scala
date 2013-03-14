package net.pgc.snippet 
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.util.Helpers._

class HelloWorld extends Logger {
  info("Test")
  def howdy = "#time *" #> Helpers.now.toString()
}

