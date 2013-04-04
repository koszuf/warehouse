name := "Warehouse system"

version := "0.2.0"

organization := "net.pgc"

scalaVersion := "2.10.0"

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

//seq(SbtStartScript.startScriptForClassesSettings: _*)

// net.virtualvoid.sbt.graph.Plugin.graphSettings // for sbt dependency-graph plugin

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "2.5-RC2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-wizard"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "textile" 	        % "2.5-RC1-1.3"      % "compile",
    "net.liftmodules"   %% "widgets"            % "2.5-RC1-1.2"      % "compile",
    "net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-2.2"),
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.9.v20130131"  % "compile,container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.9",
    "org.specs2"        % "specs2_2.10.0-M7"    % "1.12.1.1"         % "test",
    "com.h2database"    % "h2"                  % "1.3.170"
  )
}

