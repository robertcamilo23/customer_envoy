package translate_proxy.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.Future

class TranslateProxyServer(implicit val system: ActorSystem,
                           implicit val materializer: ActorMaterializer) extends TranslateProxyService {

  def startServer(address: String, port: Int): Future[Http.ServerBinding] = {
    Http().bindAndHandle(route, address, port)
  }
}

object TranslateProxyServer {

  def main(args: Array[String]) {
    implicit val actorSystem = ActorSystem("translate-proxy-server")
    implicit val materializer = ActorMaterializer()

    val server = new TranslateProxyServer()
    server.startServer("localhost", 8088)
  }
}
