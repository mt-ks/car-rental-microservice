package com.mtks.rent.services.grpc;

import com.mtks.rent.VehicleInfoRequest;
import com.mtks.rent.VehicleInfoResponse;
import com.mtks.rent.VehicleServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @GrpcClient("vehicle-service")
    VehicleServiceGrpc.VehicleServiceBlockingStub blockingStub;

    @Retryable(
            retryFor = StatusRuntimeException.class,
            backoff = @Backoff(delay = 2000)
    )
    public VehicleInfoResponse getVehicleInfo(int vehicleId) {
        try{

            VehicleInfoRequest request = VehicleInfoRequest.newBuilder()
                    .setVehicleId(vehicleId)
                    .build();
            return blockingStub.getVehicleInfo(request);
        }catch (StatusRuntimeException e){
            System.out.println(e.getStatus().getCode());
            System.out.println("********************* EXCEPTION RECEIVED**************");
            throw e;
        }
    }



}
