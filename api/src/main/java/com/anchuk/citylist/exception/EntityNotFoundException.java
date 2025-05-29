package com.anchuk.citylist.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(){
    }
    public EntityNotFoundException(String exception) {
        super(exception);
    }


}
