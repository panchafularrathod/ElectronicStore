package com.bikkadIt.electronicstore.ElectronicStore.dto;

import lombok.*;

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


    private String cartId;
    private String userId;
    private String orderStatus = "PENDING";
    private String paymentStatus="NOT PAID";
    private String billingAddress;
    private String billingPhone;
    private String billingName;



}
