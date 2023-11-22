package com.bikkadIt.electronicstore.ElectronicStore.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){

        super();
    }
    public ResourceNotFoundException(String message){
        super();
    }
}
