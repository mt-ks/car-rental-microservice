package com.mtks.rent.services.grpc;

import org.springframework.stereotype.Service;

import java.util.function.Supplier;

public class SagaStep <T>{
    private String sagaId;
    private final Supplier<T> transaction;
    private T result;
    private final Runnable compensation;

    public SagaStep(String sagaId, Supplier<T> transaction, Runnable compensation) {
        this.sagaId = sagaId;
        this.transaction = transaction;
        this.compensation = compensation;
    }

    public void commit() throws Exception {
        try {
            this.result = transaction.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public void compensate() {
        if (compensation != null) {
            compensation.run();
            System.out.println(this.getSagaId() + " Compensation executed.");
        }
    }

    public T getResult() {
        return result;
    }

    public String getSagaId(){
        return sagaId;
    }
}