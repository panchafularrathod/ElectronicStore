package com.bikkadIt.electronicstore.ElectronicStore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {
    private String email;
    private String password;

}
