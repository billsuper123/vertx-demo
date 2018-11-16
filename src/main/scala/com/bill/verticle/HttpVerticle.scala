package com.bill.verticle

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router

import scala.util.{Failure, Success}

class HttpVerticle extends ScalaVerticle{

  override def start(): Unit = {
    val router = Router.router(vertx)
    router.get("/").handler(c=>{
      c.response().end("<h1>hello Vert.x</h1>")
    })

    vertx.createHttpServer().requestHandler(router.accept(_)).listenFuture(9090).onComplete{
      case Success(result) => {
        println("Server is now listening!")
      }
      case Failure(cause) => {
        println(s"$cause")
      }
    }
    println("hello world")
  }
}
