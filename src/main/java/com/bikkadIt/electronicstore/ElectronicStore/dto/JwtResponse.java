package com.bikkadIt.electronicstore.ElectronicStore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {

    private String jwtToke;
    private UserDto user;
}
