package com.bikkadIt.electronicstore.ElectronicStore.exception;

public class BadApiRequeast extends RuntimeException{

    public BadApiRequeast(String message){

        super(message);
    }
    public BadApiRequeast(){

        super("Bad Reques !!");
    }
}
