package com.bill

import com.bill.verticle.HttpVerticle
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.{Vertx, VertxOptions}

object VertxDemoApplication extends App{
    val vertx = Vertx.vertx(VertxOptions().setWorkerPoolSize(40))
    vertx.deployVerticle(ScalaVerticle.nameForVerticle[HttpVerticle])
}
