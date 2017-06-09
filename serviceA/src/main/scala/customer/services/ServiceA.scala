package customer.services

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import customer.domain.Request
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

trait ServiceA {
  val route: Route =
    path( "servicea" / "request") {
      post {
        entity(as[Request]) {
          request: Request =>
            complete {
              s"got customer with name ${request.name}"
            }
        }
      }
    }
}
