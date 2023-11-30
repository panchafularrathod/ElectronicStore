package com.bikkadIt.electronicstore.ElectronicStore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String id;


    private String title;


    private String discription;

    private String coverImage;
}
