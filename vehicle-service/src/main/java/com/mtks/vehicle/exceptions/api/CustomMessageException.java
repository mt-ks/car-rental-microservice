package com.mtks.vehicle.exceptions.api;

public class CustomMessageException extends  RuntimeException{
    CustomMessageException(String message) {
        super(message);
    }
}
