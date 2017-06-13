package translate_proxy.infraestructure

import translate_proxy.domain.{TranslateProxy, TranslateProxyError, TranslateProxyResponse}

import scala.concurrent.Future

trait BusinessControl {
  def validate(translateProxy: TranslateProxy): Future[Either[TranslateProxyError, TranslateProxyResponse]]
}
