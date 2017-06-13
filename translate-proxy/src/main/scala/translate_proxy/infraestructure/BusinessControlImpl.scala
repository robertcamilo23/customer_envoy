package translate_proxy.infraestructure

import java.util.logging.Logger

import translate_proxy.domain._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class BusinessControlImpl extends BusinessControl {

  private[this] val logger = Logger.getLogger(classOf[BusinessControlImpl].getName)

  override def validate(translateProxy: TranslateProxy): Future[Either[TranslateProxyError, TranslateProxyResponse]] = {
    val response = getValue(translateProxy)

    response map {
      tpr => Right(tpr)
    } recover {
      case e: Exception => Left(TranslateProxyError(e.getMessage, 404))
    }
  }

  def getValue(translateProxy: TranslateProxy): Future[TranslateProxyResponse] = {
    logger.info("***********************")
    logger.info("... Consultando servicio cifrado")
    logger.info("... Consultando servicio traduccion")
    logger.info("***********************")
    val _token: String = "12345678Prueba$%&/"
    Future.successful(TranslateProxyResponse(translateProxy.name, translateProxy.age, _token))
    Future.failed(new Exception("Hubo un problema el Hp !!!"))

    //TODO Make petition to Envoy using http1 (envoy should convert that petition into gRPC)
  }
}
