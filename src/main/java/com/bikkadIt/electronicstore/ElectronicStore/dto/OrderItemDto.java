package com.bikkadIt.electronicstore.ElectronicStore.dto;

import com.bikkadIt.electronicstore.ElectronicStore.entities.Order;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private Product product;

    private Order order;
}
