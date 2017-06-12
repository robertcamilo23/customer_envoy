package translate_proxy.infraestructure
import java.util.logging.Logger

import translate_proxy.domain.{TranslateProxy, TranslateProxyResponse}


class BusinessControlImpl extends BusinessControl {

  private[this] val logger = Logger.getLogger(classOf[BusinessControlImpl].getName)

  override def validate(translateProxy: TranslateProxy): TranslateProxyResponse = {
    val _token: String = "12345678Prueba$%&/"
    logger.info("***********************")
    logger.info("... Consultando servicio cifrado")
    logger.info("... Consultando servicio traduccion")
    logger.info("***********************")
    TranslateProxyResponse(translateProxy.name, translateProxy.age, _token)
  }
}
