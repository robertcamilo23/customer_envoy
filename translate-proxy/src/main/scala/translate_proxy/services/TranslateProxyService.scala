package translate_proxy.services

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import translate_proxy.domain.{TranslateProxy, TranslateProxyResponse}
import translate_proxy.infraestructure.BusinessControlImpl
import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

trait TranslateProxyService {
  val route: Route =
    path("tproxy" / "validate") {
      post {
        entity(as[TranslateProxy]) {
          translateProxy: TranslateProxy =>
            complete {
              println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
              val _bci = new BusinessControlImpl
              val _response: TranslateProxyResponse = _bci.validate(translateProxy)
              _response
            }
        }
      }
    }
}
