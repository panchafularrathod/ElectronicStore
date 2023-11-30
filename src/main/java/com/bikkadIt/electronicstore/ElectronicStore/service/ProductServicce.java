package com.bikkadIt.electronicstore.ElectronicStore.service;

import com.bikkadIt.electronicstore.ElectronicStore.dto.ProductDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;

import java.util.List;

public interface ProductServicce {


    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, String productId);
    void deleteProduct(String productId);
    PagebleResponse<ProductDto> getAllProduct(int pageNumber,
                                             int pageSize, String sortBy, String sortDir);
    ProductDto getProduct(String productId);
    PagebleResponse<ProductDto> getAllLiveProduct(int pageNumber,
                                                  int pageSize, String sortBy, String sortDir);
    PagebleResponse<ProductDto> serachByTitle(String subTitle,int pageNumber,
                                              int pageSize, String sortBy, String sortDir);
    //create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    // update category of product
    ProductDto updateCategory(String productId, String categoryId);

    PagebleResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);

}
