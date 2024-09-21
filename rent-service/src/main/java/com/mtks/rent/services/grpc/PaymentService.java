package com.mtks.rent.services.grpc;


import com.mtks.rent.PaymentInfo;
import com.mtks.rent.PaymentRequest;
import com.mtks.rent.PaymentResponse;
import com.mtks.rent.PaymentServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @GrpcClient("payment-service")
    private PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub;

    public PaymentResponse makePayment(int vehicleId){

        PaymentInfo cardInfo = PaymentInfo.newBuilder()
                .setAmount("100")
                .setMonth(1)
                .setYear(1)
                .setCardHolder("John Doe")
                .setCvc("201")
                .setPan("MyPan")
                .setDomain("example.com")
                .setReferenceNo("REF-1212123")
                .build();

        PaymentRequest paymentRequest = PaymentRequest.newBuilder()
                .setVehicleId(vehicleId)
                .setCardInfo(cardInfo)
                .build();

        return blockingStub.makePayment(paymentRequest);
    }

}
