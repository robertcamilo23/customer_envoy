package customer.services

import java.util.UUID
import java.util.logging.Logger

import com.customer.protos.translate_proxy.{CustomerGrpc, SmartProxyReply, SmartProxyRequest}
import customer.domain.Customer
import customer.infraestructure.grpc.CustomerPlanClient
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by kamus on 5/06/17.
  */
object CustomerServer {

  private val logger = Logger.getLogger(classOf[CustomerServer].getName)

  def main(args: Array[String]) {
    val server = new CustomerServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 8080
}

class CustomerServer(executionContext: ExecutionContext) {
  self =>

  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(CustomerServer.port)
      .addService(CustomerGrpc.bindService(new CustomerImpl, executionContext))
      .build
      .start

    CustomerServer.logger.info("Server started, listening on " + CustomerServer.port)

    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class CustomerImpl extends CustomerGrpc.Customer {
    override def sendRequestRpc(request: SmartProxyRequest): Future[SmartProxyReply] = {
      val customer = Customer(UUID.fromString(request.id), request.name, request.age)
      println("******************************************")
      println("Customer: " + customer)
      println("Request: " + request)
      println("******************************************")
      CustomerPlanClient.associatePlan(customer) map { reply =>
        SmartProxyReply(message = reply.message)
      }
    }
  }

}

