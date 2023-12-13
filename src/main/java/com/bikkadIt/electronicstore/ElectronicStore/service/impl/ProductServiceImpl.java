package com.bikkadIt.electronicstore.ElectronicStore.service.impl;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.ProductDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import com.bikkadIt.electronicstore.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.electronicstore.ElectronicStore.helper.Helper;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.CategoryRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.ProductRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.ProductServicce;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServicce {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);
    private String productimagePath;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        // create random category id
        String productId= UUID.randomUUID().toString();
        productDto.setProductId(productId);
        logger.info("Initiating the dao call to save product data");
        Product product = mapper.map(productDto, Product.class);
        //added date
        product.setAddedDate(new Date());
        Product product1 = productRepository.save(product);
        logger.info("Completed the dao call to save product data");
        return mapper.map(product1, ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        logger.info("Initiating the dao call to update data with productid:{}", productId);
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        product.setAddedDate(productDto.getAddedDate());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDiscription(productDto.getDescription());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setProductImageName(productDto.getProductImageName());

        //save the entity
        Product updatedProduct = productRepository.save(product);
        logger.info("Completed the dao call to update data with productId:{}", productId);
        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        logger.info("Initiating dao call to deleted data with productId:{}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        String path = productimagePath + product.getProductImageName();
        try {
            Path path1 = Paths.get(path);
            Files.delete(path1);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        logger.info("completed dao call to deleted data with productId:{}", productId);
        productRepository.delete(product);


    }

    @Override
    public PagebleResponse<ProductDto> getAllProduct(
            int pageNumber, int pageSize, String sortBy, String sortDir) {
            Sort sort = (sortDir.equalsIgnoreCase("desc"))?
                    (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
            Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
            logger.info("Initiating dao call to retrived all data");
            Page<Product> page=productRepository.findAll(pageable);
            PagebleResponse<ProductDto> pagebleResponse = Helper.getPagebleResponse(page,ProductDto.class);
            logger.info("Completed dao call to retrived all data");
            return pagebleResponse;
    }

    @Override
    public ProductDto getProduct(String productId) {
        logger.info("Initiating the dao call for retrived single data with productId:{}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        logger.info("Completed the dao call to retrived single data with categoryId:{}", productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public  PagebleResponse<ProductDto> getAllLiveProduct(
            int pageNumber, int pageSize, String sortBy, String sortDir) {

            Sort sort = (sortDir.equalsIgnoreCase("desc"))?
                    (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
            Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        logger.info("Initiating dao call to retrived all live product data");
        Page<Product> page = productRepository.findByLiveTrue(pageable);

        PagebleResponse<ProductDto> pagebleResponse = Helper.getPagebleResponse(page, ProductDto.class);
        logger.info("Completed dao call to retrived all live product data");
        return pagebleResponse;

    }

    @Override
    public  PagebleResponse<ProductDto> serachByTitle(String subTitle,int pageNumber,
                                                      int pageSize, String sortBy, String sortDir) {

            Sort sort = (sortDir.equalsIgnoreCase("desc"))?
                    (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
            Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
        PagebleResponse<ProductDto> pagebleResponse = Helper.getPagebleResponse(page, ProductDto.class);
        return pagebleResponse;

    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {


        return null;
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        //product fetch
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public PagebleResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?
                (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByCategory(category,pageable);
        return Helper.getPagebleResponse(page, ProductDto.class);

    }
}
