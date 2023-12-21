package com.bikkadIt.electronicstore.ElectronicStore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CretateOrderRequst {

   @NotBlank(message = "Cart is require")
    private String cartId;
    @NotBlank(message = "userId is require")
    private String userId;
    private String orderStatus = "PENDING";
    private String paymentStatus="NOT PAID";
    @NotBlank(message = "Address is require")
    private String billingAddress;
    @NotBlank(message = "Phone number is require")
    private String billingPhone;
    @NotBlank(message = "name is require")
    private String billingName;



}
