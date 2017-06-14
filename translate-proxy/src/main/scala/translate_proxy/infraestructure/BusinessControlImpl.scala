package translate_proxy.infraestructure

import java.nio.ByteBuffer
import java.util.UUID
import java.util.logging.Logger

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
      case e: Exception =>
        e.printStackTrace()
        Left(TranslateProxyError(e.getMessage, 404))
    }
  }

  def makeHttpRequest(translateProxy: TranslateProxyResponse): HttpResponse[String] = {

    val request = com.customer.protos.translate_proxy.Request(
      id = UUID.randomUUID().toString,
      name = translateProxy.name,
      age = translateProxy.age
    )

    val len: Array[Byte] = 0.toByte +: ByteBuffer.allocate(4).putInt(request.toByteArray.length).array()
    val dat: Array[Byte] = request.toByteArray
    val data = len ++ dat

    val response = Http("http://0.0.0.0:8181/com.customer.protos.Customer/sendRequestRpc")
      .postData(data)
      .header("content-type", "application/grpc")
      .header("TE", "trailers")
      .asString
    println(response)
    response

  }

  def getValue(translateProxy: TranslateProxy): Future[TranslateProxyResponse] = {
    logger.info("***********************")
    logger.info("... Consultando servicio cifrado")
    logger.info("... Consultando servicio traduccion")
    logger.info("***********************")
    val _token: String = "12345678Prueba$%&/"

    Future.successful(TranslateProxyResponse(translateProxy.name, translateProxy.age, _token))
    //    Future.failed(new Exception("Error en esta jodaaa !!!!"))
  }
}
