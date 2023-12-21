package com.bikkadIt.electronicstore.ElectronicStore.dto;

import com.bikkadIt.electronicstore.ElectronicStore.entities.OrderItem;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {

    private String orderId;

    private int orderAmount;
    //PENDING, DISPATCHED, DILIVERD
    // ENUM
    private String orderStatus = "PENDING";
    //not paid , paid
    //enum
    //boolean= false=> not paid  // true=> paid
    private String paymentStatus="NOT PAID";

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;

    private Date deliveryDate;

    private UserDto userDto;

    private List<OrderItemDto> ordersItems = new ArrayList<>();
}


