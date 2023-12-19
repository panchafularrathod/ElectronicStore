package com.bikkadIt.electronicstore.ElectronicStore.entities;

import com.bikkadIt.electronicstore.ElectronicStore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private Product product;
    @ManyToOne
    private Order order;
}
