package com.bikkadIt.electronicstore.ElectronicStore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
@Builder
public class Category {
    @Id
    @Column(name = "CategoryId")
    private String id;

    @Column(name="categoryTital", length = 60,nullable = false)
    private String title;

    @Column(name= "category_discription", length=500)
    private String discription;

    private String coverImage;
}
