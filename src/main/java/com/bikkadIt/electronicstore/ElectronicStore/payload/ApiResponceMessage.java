package com.bikkadIt.electronicstore.ElectronicStore.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class ApiResponceMessage {
    private String message;
    private boolean success;
}


