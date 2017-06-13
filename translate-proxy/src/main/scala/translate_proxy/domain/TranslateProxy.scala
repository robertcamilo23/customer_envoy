package translate_proxy.domain

import java.util.UUID

case class TranslateProxy(name: String, age: Int)

case class TranslateProxyResponse(name: String, age: Int, token: String)

case class TranslateProxyError(name: String, status: Int)

case class Customer(id: UUID, name: String, age: Int)
