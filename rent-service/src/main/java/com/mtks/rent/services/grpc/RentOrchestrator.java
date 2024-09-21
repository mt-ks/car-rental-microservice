package com.mtks.rent.services.grpc;

import com.mtks.rent.PaymentInfo;
import com.mtks.rent.PaymentResponse;
import com.mtks.rent.VehicleInfoResponse;
import com.mtks.rent.services.OrderService;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class RentOrchestrator {

    public VehicleService vehicleService;
    public PaymentService paymentService;
    public OrderService orderService;
    public SagaStepExecutor sagaStepExecutor;
    @Autowired
    public RentOrchestrator(VehicleService vehicleService, PaymentService paymentService, OrderService orderService, SagaStepExecutor sagaStepExecutor) {
        this.vehicleService = vehicleService;
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.sagaStepExecutor = sagaStepExecutor;
    }

    private final static class SagaID{
        private final static String CreateOrder = "createOrder";
        private final static String CompleteOrder = "completeOrder";
        private final static String CheckVehicle = "checkVehicle";
        private final static String MakePayment = "makePayment";
    }

    List<SagaStep<?>> steps = new ArrayList<>();

    public void createOrderSaga(int vehicleId) {

        steps.add(new SagaStep<>(
                SagaID.CreateOrder,
                () -> orderService.create(vehicleId),
                () -> {
                    SagaStep<Integer> sagaStep = this.sagaStepExecutor.getSaga(SagaID.CreateOrder,Integer.class);
                    if (sagaStep == null || sagaStep.getResult() == null) {
                        // order creation failed.
                        return;
                    }
                    int orderId = sagaStep.getResult();
                    orderService.delete(orderId);
                }
        ));


        steps.add(new SagaStep<>(
                SagaID.CheckVehicle,
                () -> {
                    VehicleInfoResponse vehicleInfoResponse = vehicleService.getVehicleInfo(vehicleId);
                    if(!vehicleInfoResponse.getIsAvailable()) {
                        throw new RuntimeException("Vehicle is not available");
                    }
                    return vehicleInfoResponse;
                },
                () -> {
                    // add your compensation logic.
                }
        ));

        steps.add(new SagaStep<>(
                SagaID.MakePayment,
                () -> {
                    SagaStep<VehicleInfoResponse> item = this.sagaStepExecutor.getSaga(SagaID.CheckVehicle,VehicleInfoResponse.class);
                    if(item == null || item.getResult() == null){
                        throw new RuntimeException("Vehicle response error!");
                    }

                    VehicleInfoResponse vehicle = item.getResult();
                    return paymentService.makePayment(vehicle.getId());

                    // throw new RuntimeException("Payment Error");
                },
                () -> {
                   // paymentService.refund();
                }

        ));
        steps.add(new SagaStep<>(
                SagaID.CompleteOrder,
                () -> {

                    SagaStep<Integer> createOrderSaga = this.sagaStepExecutor.getSaga(SagaID.CreateOrder,Integer.class);
                    if(createOrderSaga == null || createOrderSaga.getResult() == null){
                        throw new RuntimeException("Order response error!");
                    }
                    int orderId = createOrderSaga.getResult();
                    orderService.complete(orderId);
                    throw new RuntimeException("Order response error!");

                },
                () -> {}
        ));


        this.sagaStepExecutor.setSteps(steps);
        this.sagaStepExecutor.execute();
    }


}
