package com.mtks.vehicle.services.grpc;

import com.mtks.vehicle.Vehicle;
import com.mtks.vehicle.VehicleInfoRequest;
import com.mtks.vehicle.VehicleInfoResponse;
import com.mtks.vehicle.VehicleServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class RentalService extends VehicleServiceGrpc.VehicleServiceImplBase {


    @Override
    public void getVehicleInfo(VehicleInfoRequest request, StreamObserver<VehicleInfoResponse> responseObserver) {
        try{
            int vehicleId = request.getVehicleId();
            //VehicleResponseDto vehicle = vehicleService.findById(vehicleId);

            VehicleInfoResponse response = VehicleInfoResponse.newBuilder()
                    .setId(1)
                    .setPrice(31F)
                    .setIsAvailable(true)
                    .setSecurityDeposit(120F)
                    .build();

            System.out.println(response.toString());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
