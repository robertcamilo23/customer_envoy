package com.customer.protos.translate_proxy

object CustomerGrpc {
  val METHOD_SEND_REQUEST_RPC: _root_.io.grpc.MethodDescriptor[com.customer.protos.translate_proxy.Request, com.customer.protos.translate_proxy.Reply] =
    _root_.io.grpc.MethodDescriptor.create(
      _root_.io.grpc.MethodDescriptor.MethodType.UNARY,
      _root_.io.grpc.MethodDescriptor.generateFullMethodName("com.customer.protos.Customer", "sendRequestRpc"),
      new com.trueaccord.scalapb.grpc.Marshaller(com.customer.protos.translate_proxy.Request),
      new com.trueaccord.scalapb.grpc.Marshaller(com.customer.protos.translate_proxy.Reply))
  
  trait Customer extends _root_.com.trueaccord.scalapb.grpc.AbstractService {
    override def serviceCompanion = Customer
    def sendRequestRpc(request: com.customer.protos.translate_proxy.Request): scala.concurrent.Future[com.customer.protos.translate_proxy.Reply]
  }
  
  object Customer extends _root_.com.trueaccord.scalapb.grpc.ServiceCompanion[Customer] {
    implicit def serviceCompanion: _root_.com.trueaccord.scalapb.grpc.ServiceCompanion[Customer] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.customer.protos.translate_proxy.TranslateProxyProto.javaDescriptor.getServices().get(0)
  }
  
  trait CustomerBlockingClient {
    def serviceCompanion = Customer
    def sendRequestRpc(request: com.customer.protos.translate_proxy.Request): com.customer.protos.translate_proxy.Reply
  }
  
  class CustomerBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[CustomerBlockingStub](channel, options) with CustomerBlockingClient {
    override def sendRequestRpc(request: com.customer.protos.translate_proxy.Request): com.customer.protos.translate_proxy.Reply = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_SEND_REQUEST_RPC, options), request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): CustomerBlockingStub = new CustomerBlockingStub(channel, options)
  }
  
  class CustomerStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[CustomerStub](channel, options) with Customer {
    override def sendRequestRpc(request: com.customer.protos.translate_proxy.Request): scala.concurrent.Future[com.customer.protos.translate_proxy.Reply] = {
      com.trueaccord.scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_SEND_REQUEST_RPC, options), request))
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): CustomerStub = new CustomerStub(channel, options)
  }
  
  def bindService(serviceImpl: Customer, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder("com.customer.protos.Customer")
    .addMethod(
      METHOD_SEND_REQUEST_RPC,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.customer.protos.translate_proxy.Request, com.customer.protos.translate_proxy.Reply] {
        override def invoke(request: com.customer.protos.translate_proxy.Request, observer: _root_.io.grpc.stub.StreamObserver[com.customer.protos.translate_proxy.Reply]): Unit =
          serviceImpl.sendRequestRpc(request).onComplete(com.trueaccord.scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): CustomerBlockingStub = new CustomerBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): CustomerStub = new CustomerStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.customer.protos.translate_proxy.TranslateProxyProto.javaDescriptor.getServices().get(0)
  
}