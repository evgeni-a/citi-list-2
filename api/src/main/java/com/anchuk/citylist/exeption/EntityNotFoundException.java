package com.anchuk.citylist.exeption;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(){
    }
    public EntityNotFoundException(String exception) {
        super(exception);
    }


}
