package com.bikkadIt.electronicstore.ElectronicStore.entities;

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
@Entity
@Table(name="Item_Order")
public class Order {

    @Id
    private String orderId;

    ////PENDING, DISPATCHED, DILIVERD

    // ENUM

    private String orderStatus;

    //not paid paid
    //enum
    //boolean= false=> not paid  // true=> paid
    private String paymentStatus;
    private int orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;

    private Date deliveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private  User user;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<OrderItem> ordersItems = new ArrayList<>();
}
