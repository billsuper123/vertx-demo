package com.bill.handler

import io.vertx.scala.ext.web.RoutingContext

object IndexHandler {

  def apply(): IndexHandler = new IndexHandler()
}

class IndexHandler{
  def index(context:RoutingContext)={
    context.response().end("<h1>hello Vert.x</h1>")
  }
}