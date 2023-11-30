package com.bikkadIt.electronicstore.ElectronicStore.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class ApiResponceMessage {
    private String message;
    private boolean success;
    private HttpStatus status;
}


