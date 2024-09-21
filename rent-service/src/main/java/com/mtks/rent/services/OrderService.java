package com.mtks.rent.services;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public Integer create(int vehicleId){
        return vehicleId;
    }

    public Integer complete(int orderId){
        return orderId;
    }

    public void delete(int orderId){

    }

}
