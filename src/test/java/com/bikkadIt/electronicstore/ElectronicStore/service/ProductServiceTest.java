package com.bikkadIt.electronicstore.ElectronicStore.service;

import com.bikkadIt.electronicstore.ElectronicStore.dto.ProductDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.ProductRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class ProductServiceTest {



        @MockBean
        private ProductRepository productRepository;
        @Autowired
        private ProductServiceImpl productService;
        @Autowired
        private ModelMapper modelMapper;
        Product product;

        @BeforeEach
        public void init() {
            product = Product.builder()
                    .productId(UUID.randomUUID().toString())
                    .title("iphone")
                    .discription("this phone having good camera and 5G features")
                    .price(70000)
                    .quantity(2)
                    .stock(true)
                    .live(true)
                    .productImageName("ipn.png").build();
        }

        @Test
        public void createProductTest() {
            String productId = product.getProductId();
            Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
            ProductDto product1 = productService.createProduct(modelMapper.map(product, ProductDto.class));
            System.out.println(product1.getTitle());
            Assertions.assertEquals("iphone", product1.getTitle());
            Assertions.assertNotNull(product1);
        }

        @Test
        public void updateProductTest() {
            String productId = product.getProductId();
            ProductDto productDto = ProductDto.builder()
                    .productId(UUID.randomUUID().toString())
                    .title("RedmiNote6")
                    .description("this phone having good camera and 5G features")
                    .price(60000)
                    .quantity(2)
                    .discountPrice(15000)
                    .stock(true)
                    .live(true)
                    .productImageName("ipn.png").build();
            Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
            Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
            ProductDto updateProduct = productService.updateProduct(productDto, productId);
            System.out.println(updateProduct.getTitle());
            Assertions.assertNotNull(updateProduct);
            Assertions.assertEquals("RedmiNote6", updateProduct.getTitle());
        }

        @Test
        public void getProductByIdTest() {
            String productId = product.getProductId();
            Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
            ProductDto productDto = productService.getProduct(productId);
            System.out.println(productDto);
            Assertions.assertEquals("iphone", productDto.getTitle());
        }

    @Test
    public void deleteproductTest() {
        String productId = "fhgdk";
        Mockito.when(productRepository.findById("fhgdk")).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
        @Test
        public void getAllProductest() {
            Product product1 = Product.builder()
                    .productId(UUID.randomUUID().toString())
                    .title("Motrolo")
                    .discription("this phone havin good camera and 5G features")
                    .price(70000)
                    .quantity(2)
                    .discountPrice(10000)
                    .stock(true)
                    .live(true)
                    .productImageName("ipn.png").build();
            Product product2 = Product.builder()
                    .productId(UUID.randomUUID().toString())
                    .title("Nokia 7")
                    .discription("this phone having good camera and 5G features")
                    .price(70000)
                    .quantity(2)
                    .discountPrice(10000)
                    .stock(true)
                    .live(true)
                    .productImageName("ipn.png").build();
            List<Product> list = Arrays.asList(product, product1, product2);
            Page<Product> page = new PageImpl<>(list);
            Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
            PagebleResponse<ProductDto> allProduct = productService.getAllProduct(1, 2, "title", "asc");
            int size = allProduct.getContent().size();
            Assertions.assertEquals(3, size);
        }




    }



