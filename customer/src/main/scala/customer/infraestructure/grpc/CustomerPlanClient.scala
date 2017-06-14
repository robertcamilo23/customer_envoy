package customer.infraestructure.grpc

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import com.customer.protos.customer_plan.CustomerPlanGrpc.CustomerPlanBlockingStub
import com.customer.protos.customer_plan.{CustomerPlanGrpc, Reply, Request}
import customer.domain.Customer
import io.grpc.{ManagedChannel, ManagedChannelBuilder, Status, StatusRuntimeException}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by kamus on 5/06/17.
  */
object CustomerPlanClient {
  def apply(host: String, port: Int): CustomerPlanClient = {
    val channel = ManagedChannelBuilder.forTarget(s"$host:$port").usePlaintext(true).build
    val blockingStub = CustomerPlanGrpc.blockingStub(channel)
    new CustomerPlanClient(channel, blockingStub)
  }

  def associatePlan(customer: Customer): Future[Reply] = {
    Future {
      val cusPlan = CustomerPlanClient("localhost", 50051)
      val plan = if (customer.age <= 20) "Plan_1"
      else if (customer.age > 20 && customer.age <= 40) "Plan_2"
      else "Plan_3"

      val reply: Reply = cusPlan.associateCustomerToPlan(customer.id.toString, plan)
      cusPlan.shutdown()
      reply
    }
  }
}

class CustomerPlanClient private(
                                  private val channel: ManagedChannel,
                                  private val blockingStub: CustomerPlanBlockingStub
                                ) {
  private[this] val logger = Logger.getLogger(classOf[CustomerPlanClient].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def associateCustomerToPlan(customerId: String, customerPlan: String): Reply = {
    val request = Request(customerId, customerPlan)
    try {
      val response: Reply = blockingStub.associateCustomerToPlan(request)
      logger.info("Response message: " + response.message)
      response
    } catch {
      case e: StatusRuntimeException =>
        logger.log(Level.SEVERE, "RPC failed: {0}", e.getStatus)
        throw new StatusRuntimeException(Status.INTERNAL)
    }
  }

}
