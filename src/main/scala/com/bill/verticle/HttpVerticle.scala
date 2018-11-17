package com.bill.verticle

import com.bill.handler.{IndexHandler, PersonHandler}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.config.ConfigRetriever
import io.vertx.scala.ext.web.Router

import scala.util.{Failure, Success}

class HttpVerticle extends ScalaVerticle{

  override def start(): Unit = {
    val router = Router.router(vertx)
    val indexHandler = IndexHandler()
    val personHandler = PersonHandler()
    router.get("/").handler(indexHandler.index(_))

    router.get("/person").handler(personHandler.index(_))
    router.get("/person/:id").handler(personHandler.id(_))

    val retriever = ConfigRetriever.create(vertx)
    retriever.getConfigFuture().onComplete{
        case Success(value) =>{
          println(value)
        }
        case Failure(exception)=>{
          println(s"${exception}")
        }
    }

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
