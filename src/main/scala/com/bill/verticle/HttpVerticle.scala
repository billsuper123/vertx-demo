package com.bill.verticle

import com.bill.handler.{IndexHandler, PersonHandler}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.config.ConfigRetriever
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.{BodyHandler, CookieHandler, SessionHandler}
import io.vertx.scala.ext.web.sstore.LocalSessionStore

import scala.util.{Failure, Success}

object HttpVerticle{
  val BODY_SIZE = 1024*1024*1024
  val HTTP_PORT = 9090
}

class HttpVerticle extends ScalaVerticle{

  override def start(): Unit = {
    val router = Router.router(vertx)
    router.route().handler(BodyHandler.create().setBodyLimit(HttpVerticle.BODY_SIZE))
    router.route().handler(CookieHandler.create)
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setCookieHttpOnlyFlag(true).setCookieSecureFlag(true))

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

    vertx.createHttpServer().requestHandler(router.accept(_)).listenFuture(HttpVerticle.HTTP_PORT).onComplete{
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
