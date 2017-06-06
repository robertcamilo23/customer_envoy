package customer.infraestructure.grpc

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import com.customer.protos.customer_plan.{CustomerPlanGrpc, Request}
import com.customer.protos.customer_plan.CustomerPlanGrpc.{CustomerPlanBlockingStub, CustomerPlanStub}
import customer.domain.Customer
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}

/**
  * Created by kamus on 5/06/17.
  */
object CustomerPlanClient {
  def apply(host: String, port: Int): CustomerPlanClient = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build
    val blockingStub = CustomerPlanGrpc.blockingStub(channel)
    new CustomerPlanClient(channel, blockingStub)
  }

  def associatePlan( customer: Customer ): Unit ={
    val cusPlan = CustomerPlanClient("localhost", 50051)
    try {
      val plan = if (customer.age <= 20) "Plan_1"
      else if (customer.age > 20 && customer.age <= 40) "Plan_2"
      else "Plan_3"

      cusPlan.associateCustomerToPlan( customer.id.toString, plan )
    } finally {
      cusPlan.shutdown()
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

  def associateCustomerToPlan( customerId: String, customerPlan: String ): Unit = {
    val request: Request = Request(customerId, customerPlan )
    try{
      val response = blockingStub.associateCustomerToPlan( request )
      logger.info("Response message: " + response.message)
    } catch {
      case e: StatusRuntimeException =>
        logger.log(Level.SEVERE, "RPC failed: {0}", e.getStatus)
    }
  }

}