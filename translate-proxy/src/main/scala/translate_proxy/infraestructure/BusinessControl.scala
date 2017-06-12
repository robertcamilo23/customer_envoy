package translate_proxy.infraestructure

import translate_proxy.domain.{TranslateProxy, TranslateProxyResponse}

trait BusinessControl {
  def validate(translateProxy: TranslateProxy) : TranslateProxyResponse
}
