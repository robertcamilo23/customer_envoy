package translate_proxy.infraestructure
import java.util.logging.Logger

import translate_proxy.domain.{TranslateProxy, TranslateProxyResponse}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class BusinessControlImpl extends BusinessControl {

  private[this] val logger = Logger.getLogger(classOf[BusinessControlImpl].getName)

  override def validate(translateProxy: TranslateProxy): Future[TranslateProxyResponse] = {
    val _token: String = "12345678Prueba$%&/"
    logger.info("***********************")
    logger.info("... Consultando servicio cifrado")
    logger.info("... Consultando servicio traduccion")
    logger.info("***********************")
    Future.successful(TranslateProxyResponse(translateProxy.name, translateProxy.age, _token))
    /*Future {
      throw new Exception("Hubo un problema el Hp !!!")
    }*/
  }
}
