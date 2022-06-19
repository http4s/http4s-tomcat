/*
 * Copyright 2014 http4s.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example

import cats.effect._
import org.http4s._
import org.http4s.server.Server
import org.http4s.tomcat.server.TomcatBuilder

import javax.servlet.FilterChain
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/** 1. Run as `sbt examples/run`
  * 2. Browse to http://localhost:8080/http4s to see `httpRoutes`
  * 3. Browse to http://localhost:8080/raw to see a raw servlet alongside the http4s routes
  * 4. Browse to http://localhost:8080/raw/black-knight to see a route with a servlet filter
  */
object TomcatExample extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    new TomcatExample[IO].resource.use(_ => IO.never).as(ExitCode.Success)
}

class TomcatExample[F[_]](implicit F: Async[F]) {
  def httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case req if req.method == Method.GET =>
      Sync[F].pure(Response(Status.Ok).withEntity("/pong"))
  }

  // Also supports raw servlets alongside your http4s routes
  val rawServlet: HttpServlet = new HttpServlet {
    override def doGet(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Unit =
      response.getWriter.print("Raw servlet")
  }

  // Also supports raw filters alongside your http4s routes
  val filter: HttpFilter = new HttpFilter {
    override protected def doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ): Unit = {
      response.setStatus(403)
      response.getWriter.print("None shall pass!")
    }
  }

  def resource: Resource[F, Server] =
    TomcatBuilder[F]
      .bindHttp(8080)
      .mountService(httpRoutes, "/http4s")
      .mountServlet(rawServlet, "/raw/*")
      .mountFilter(filter, "/raw/black-knight/*")
      .resource
}
