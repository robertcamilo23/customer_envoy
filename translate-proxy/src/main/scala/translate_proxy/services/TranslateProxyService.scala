package translate_proxy.services

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import translate_proxy.domain.{TranslateProxy, TranslateProxyError, TranslateProxyResponse}
import translate_proxy.infraestructure.BusinessControlImpl
import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait TranslateProxyService {
  val route: Route =
    path("tproxy" / "validate") {
      post {
        entity(as[TranslateProxy]) {
          translateProxy: TranslateProxy =>
            complete {
              val _bci = new BusinessControlImpl
              val _response: Future[TranslateProxyResponse] = _bci.validate(translateProxy)
              _response onComplete {
                case Success(a) => a
                case Failure(e) =>
                  e.printStackTrace()
                  TranslateProxyError("sssssssssss")
              }
            }
        }
      }
    }
}
