package com.bikkadIt.electronicstore.ElectronicStore.dto;

import com.bikkadIt.electronicstore.ElectronicStore.entities.Order;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private Product product;

    private Order order;
}
