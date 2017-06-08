package customer.services

import java.util.UUID

import customer.domain.Customer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import customer.infraestructure.grpc.CustomerPlanClient
import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

trait CustomerService {
  val route: Route =
    path("customer") {
      post {
        entity(as[Customer]) {
          customer: Customer =>
            complete {
              CustomerPlanClient.associatePlan(
                customer.copy( id = UUID.randomUUID() ) )

              s"got customer with name ${customer.name}"
            }
        }
      }
    }
}
