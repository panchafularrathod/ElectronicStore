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
@Entity
@Table(name="Carts")
@Builder
public class Cart {
    @Id
    private String cartId;

    private Date createAt;

    @OneToOne
    private User user;

    // mapping cart Item
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> items=new ArrayList<>();
}
