package translate_proxy.infraestructure

import java.util.UUID
import java.util.logging.Logger

import com.google.gson.Gson
import translate_proxy.domain._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaj.http.{Http, HttpResponse}


class BusinessControlImpl extends BusinessControl {

  private[this] val logger = Logger.getLogger(classOf[BusinessControlImpl].getName)

  override def validate(translateProxy: TranslateProxy): Future[Either[TranslateProxyError, TranslateProxyResponse]] = {
    val response = getValue(translateProxy)

    response map {
      tpr =>
        //TODO Make petition to Envoy using http1 (envoy should convert that petition into gRPC)
        val a = makeHttpRequest(tpr)
        Right(TranslateProxyResponse(a.body, a.code, "ok"))
    } recover {
      case e: Exception => Left(TranslateProxyError(e.getMessage, 404))
    }
  }

  def makeHttpRequest(translateProxy: TranslateProxyResponse): HttpResponse[String] = {

    val gson = new Gson()
    val customer = new Customer(id = UUID.randomUUID(), name = translateProxy.name, age = translateProxy.age)

    val response = Http("http://0.0.0.0:8181/customer")
      .postData(gson.toJson(customer).stripMargin)
      .header("Content-Type", "application/json")
      .asString
    response

  }

  def getValue(translateProxy: TranslateProxy): Future[TranslateProxyResponse] = {
    logger.info("***********************")
    logger.info("... Consultando servicio cifrado")
    logger.info("... Consultando servicio traduccion")
    logger.info("***********************")
    val _token: String = "12345678Prueba$%&/"

    Future.successful(TranslateProxyResponse(translateProxy.name, translateProxy.age, _token))
    //Future.failed(new Exception("Error en esta jodaaa !!!!"))
  }
}
