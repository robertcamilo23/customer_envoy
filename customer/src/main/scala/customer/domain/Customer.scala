package customer.domain

import java.util.UUID

case class Customer(
  id: UUID,
  name: String,
  age: Int
)
