package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CategoryDto;
import com.bikkadIt.electronicstore.ElectronicStore.dto.ProductDto;
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

    /**
     * @param categoryDto
     * @return categoryDto1
     * @auther panchafula
     * @apiNote to save Category data into database
     */
 @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){

     logger.info("Entering the request to save category data");
      // call service to save object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
     logger.info("completed the request by saving category data");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }
    /**
     * @param categoryId
     * @return updateCategory
     * @auther panchafula
     * @apiNote to update Category data in the database
     */
    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto){
        logger.info("Entering the request for updating category data with categoryId:{}", categoryId);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        logger.info("completed the request for updating category data with categoryId:{}", categoryId);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);

    }

    /**
     * @param categoryId
     * @return
     * @auther panchafula
     * @apiNote to delete single Cateogry data from database
     */
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

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return allCategories
     * @auther panchafula
     * @apiNote to get all Categories data from database
     */
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

    /**
     * @param categoryId
     * @return categoryDto
     * @auther panchafula
     * @apiNote to deleted category data at given categoryId from database
     */
    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> getByCategoryId(@PathVariable String categoryId){

            logger.info("Entering the request for get single category data with category{}",categoryId);
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        logger.info("Complete the request for get single category data with category{}",categoryId);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);

        }
        //cover image upload
    /**
     * @param categoryId
     * @return imageResponse
     * @auther panchafula
     * @apiNote to upload image at given categoryId
     */

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage
            (@RequestParam("coverImage") MultipartFile image,
             @PathVariable String categoryId) throws IOException {
        logger.info("Entering the request for uploading image with categoryId:{}");
        String imageName = fileService.uploadFile(image, imageUploadPath);
        CategoryDto category = categoryService.getCategory(categoryId);
        category.setCoverImage(imageName);
        categoryService.updateCategory(category, categoryId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).
                success(true).status(HttpStatus.CREATED).build();
        logger.info("completed the request for uploading image with categoryId:{}");
        return new ResponseEntity <>(imageResponse,HttpStatus.CREATED);
    }
    /**
     * @param categoryId
     * @return
     * @auther panchafula
     * @apiNote to serv image at given categoryId
     * @throws IOException
     */

    @GetMapping(value ="/image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto category = categoryService.getCategory(categoryId);
        logger.info("cover  image name : {}",category.getCoverImage());
        InputStream resource =fileService.getResource(imageUploadPath,category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

    /**
     * @param categoryId
     * @return productWithCategory
     * @auther panchafula
     * @apiNote to create product with category
     */


    // create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable ("categoryId") String categoryId,
                                                                @RequestBody ProductDto productDto){
        logger.info("Entering the request for creting product with category");
        ProductDto productWithCategory = productServicce.createWithCategory(productDto, categoryId);
        logger.info("completed the request for creating product with category");
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



