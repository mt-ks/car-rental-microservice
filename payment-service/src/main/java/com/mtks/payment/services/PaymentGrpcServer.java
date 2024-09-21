package com.mtks.payment.services;


import com.mtks.payment.PaymentRequest;
import com.mtks.payment.PaymentResponse;
import com.mtks.payment.PaymentServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PaymentGrpcServer  extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void makePayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        PaymentResponse response = PaymentResponse.newBuilder()
                .setStatus("COMPLETEDx:x")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
