package translate_proxy.services

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import translate_proxy.domain.TranslateProxy
import translate_proxy.infraestructure.BusinessControlImpl

trait TranslateProxyService {
  val route: Route =
    path("tproxy" / "validate") {
      post {
        entity(as[TranslateProxy]) {
          translateProxy: TranslateProxy =>
            complete {
              val _bci = new BusinessControlImpl
              _bci.validate(translateProxy)
            }
        }
      }
    }
}
