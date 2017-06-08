import java.util.UUID
import java.util.logging.Logger

import com.customer.protos.customer_plan.{CustomerPlanGrpc, Reply, Request}
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by kamus on 5/06/17.
  */
object CustomerPlanServer {
  private val logger = Logger.getLogger(classOf[CustomerPlanServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new CustomerPlanServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 50053
}

class CustomerPlanServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(CustomerPlanServer.port)
      .addService(CustomerPlanGrpc.bindService(new GreeterImpl, executionContext))
      .build
      .start

    CustomerPlanServer.logger.info("Server started, listening on " + CustomerPlanServer.port)

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

  private class GreeterImpl extends CustomerPlanGrpc.CustomerPlan {
    override def associateCustomerToPlan(request: Request): Future[Reply] = {
      val reply = Reply( s"Associated: ${UUID.randomUUID()}" )
      println( s"Request: $request" )
      println( s"Reply: $reply" )
      Future.successful(reply)
    }
  }

}
