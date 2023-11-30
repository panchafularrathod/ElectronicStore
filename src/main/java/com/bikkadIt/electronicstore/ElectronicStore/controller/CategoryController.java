package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.config.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CategoryDto;
import com.bikkadIt.electronicstore.ElectronicStore.dto.ProductDto;
import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ImageResponse;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.service.CategoryService;
import com.bikkadIt.electronicstore.ElectronicStore.service.FileService;
import com.bikkadIt.electronicstore.ElectronicStore.service.ProductServicce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(Category.class);


    @Autowired
    private FileService fileService;
    @Value("${category.image.path}")
    private String imageUploadPath;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductServicce productServicce;

    //create
 @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
      // call service to save object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }
    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto){
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);

    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponceMessage> deleteCategory(@PathVariable String categoryId){
        logger.info("Entering the request for delete the category data with categoryid{} ");
     categoryService.deleteCategory(categoryId);
        ApiResponceMessage responce = ApiResponceMessage.builder().message("deleted successfully")
                .status(HttpStatus.OK).success(true).build();
        logger.info("Complete the request for delete the category data with categoryId");
        return new ResponseEntity<>(responce,HttpStatus.OK);

    }
    @GetMapping("/")
    public ResponseEntity<PagebleResponse<CategoryDto>> getAllCategory
            (@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
             @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
             @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
        logger.info("Entering the request for get the all Category data ");

        PagebleResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for get the all Category data ");
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> getByCategoryId(@PathVariable String categoryId){

            logger.info("Entering the request for get single category data with category{}",categoryId);
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        logger.info("Complete the request for get single category data with category{}",categoryId);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);

        }
        //cover image upload

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage
            (@RequestParam("coverImage") MultipartFile image,
             @PathVariable String categoryId) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);
        CategoryDto category = categoryService.getCategory(categoryId);
        category.setCoverImage(imageName);
        categoryService.updateCategory(category, categoryId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).
                success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity <>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping(value ="/image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto category = categoryService.getCategory(categoryId);
        logger.info("cover  image name : {}",category.getCoverImage());
        InputStream resource =fileService.getResource(imageUploadPath,category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

    // create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable ("categoryId") String categoryId,
                                                                @RequestBody ProductDto productDto){
        ProductDto productWithCategory = productServicce.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);

    }
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId, @PathVariable String productId){

        ProductDto productDto = productServicce.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    // get product of category
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PagebleResponse<ProductDto>>getProductOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){

        PagebleResponse<ProductDto> response = productServicce.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}



