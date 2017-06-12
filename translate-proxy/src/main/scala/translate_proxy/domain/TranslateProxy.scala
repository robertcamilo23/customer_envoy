package translate_proxy.domain

trait TranslateResponse

case class TranslateProxy (name: String, age: Int)

case class TranslateProxyResponse (name: String, age: Int, token: String) extends TranslateResponse

case class TranslateProxyError (message: String) extends TranslateResponse
