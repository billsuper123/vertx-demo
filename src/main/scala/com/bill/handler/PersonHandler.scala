package com.bill.handler

import java.util.Date

import com.bill.domian.{Address, Person}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.vertx.core.json.Json
import io.vertx.scala.ext.web.RoutingContext

object PersonHandler {
  lazy private val persons = List(Person(1,"bill",new Date(),List(Address("liwan","123456"),Address("tianhe","654321"))),
    Person(2,"julianna",new Date(),List(Address("china","fdkjgkl;d"),Address("japan","654321"))))
  Json.prettyMapper.registerModule(DefaultScalaModule)
  def apply(): PersonHandler = new PersonHandler()

}

class PersonHandler{
  def index(context:RoutingContext) = {
    context.response().putHeader("content-type","application/json;charset=utf-8").end(Json.encodePrettily(PersonHandler.persons))
  }

  def id(context: RoutingContext) = {
    val id = context.request().getParam("id").getOrElse("0").toInt
    val person = PersonHandler.persons.filter{case Person(pid,_,_,_) => pid == `id`} match {
      case List(p@Person(_,_,_,_)) => p
      case _ => null
    }
    context.response()
      .putHeader("content-type","application/json;charset=utf-8")
      .end(Json.encodePrettily(person))
  }
}
