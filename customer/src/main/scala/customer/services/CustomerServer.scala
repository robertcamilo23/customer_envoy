package customer.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.Future

/**
  * Created by kamus on 5/06/17.
  */
class CustomerServer(implicit
                     val system: ActorSystem,
                     implicit val materializer: ActorMaterializer) extends CustomerService {

  def startServer(address: String, port: Int): Future[Http.ServerBinding] = {
    Http().bindAndHandle(route, address, port)
  }
}

object CustomerServer {

  def main(args: Array[String]) {
    implicit val actorSystem = ActorSystem("customer-server")
    implicit val materializer = ActorMaterializer()

    val server = new CustomerServer()
    println( "Starting server ..." )
    server.startServer("0.0.0.0", 8080)
    println( "Server started!" )
  }
}