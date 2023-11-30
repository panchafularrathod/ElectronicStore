package com.bikkadIt.electronicstore.ElectronicStore.exception;

import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage.*;

@RestControllerAdvice
public class GlobleExceptionHalder {

        private Logger logger= LoggerFactory.getLogger(GlobleExceptionHalder.class);

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponceMessage> resouceNotFoundExceptionHandler(ResourceNotFoundException ex){
            logger.info("Exception Handler invoked..");
            ApiResponceMessage response=  builder().message(ex.getMessage()).success(true).build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        //   MethodargumentNotValidException
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> HandleMethodArgsValidException(MethodArgumentNotValidException ex){

            Map<String, String> resp = new HashMap<>();

            ex.getBindingResult().getAllErrors().forEach((error)->{
                String fieldname = ((FieldError)error).getField();
                String message = error.getDefaultMessage();resp.put(fieldname, message);
            });
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);

        }
    @ExceptionHandler(BadApiRequeast.class)
    public ResponseEntity<ApiResponceMessage> handleBadApiRequest(BadApiRequeast ex){
        logger.info("Bad Api Request..");
        ApiResponceMessage response=  builder().message(ex.getMessage()).success(false).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    }


