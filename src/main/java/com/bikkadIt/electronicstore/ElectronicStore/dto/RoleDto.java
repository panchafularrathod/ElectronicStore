package com.bikkadIt.electronicstore.ElectronicStore.dto;

import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {

    private String roleId;
    private String roleName;


}
