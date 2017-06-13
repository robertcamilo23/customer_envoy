package customer.adapters

import customer.domain.Request

/**
  * Created by kamus on 12/06/17.
  */
object CIAdapter {

  def callFirstService( r: Request ): Unit ={
    println( s"First service called: $r" )
  }

  def callSecondService( r: Request ): Unit ={
    println( s"Second service called: $r" )
  }
}
