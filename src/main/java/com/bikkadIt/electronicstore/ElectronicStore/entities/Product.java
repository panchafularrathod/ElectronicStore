package com.bikkadIt.electronicstore.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Electronic_Products")
@Builder
public class Product {

    @Id
    private  String productId;

    @Column(name="product_Title")
    private String title;
    @Column(length = 10000)
    private String discription;
    private int price;
    private int quantity;
    @Column(name = "added_date")
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private int discountPrice;
    private String productImageName;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "product") // This should match the field name in OrderItem
    private List<OrderItem> orderItems;


}
