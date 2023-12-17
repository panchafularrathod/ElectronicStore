package com.bikkadIt.electronicstore.ElectronicStore.dto;

import com.bikkadIt.electronicstore.ElectronicStore.entities.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createAt;
    private UserDto userDto;
    private List<CartItem> items=new ArrayList<>();
}
