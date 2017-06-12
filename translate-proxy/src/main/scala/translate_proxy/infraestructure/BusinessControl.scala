package translate_proxy.infraestructure

import translate_proxy.domain.{TranslateProxy, TranslateProxyResponse}

import scala.concurrent.Future

trait BusinessControl {
  def validate(translateProxy: TranslateProxy) : Future[TranslateProxyResponse]
}
