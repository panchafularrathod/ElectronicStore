package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.ProductDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ImageResponse;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
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
@RequestMapping("/products")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(Product.class);
    @Autowired
    private ProductServicce productServicce;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imageUploadPath;

    /**
     * @param  productDto
     * @return createproduct
     * @auther panchafula
     * @apiNote to search for live product
     * */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        logger.info("Entering the request for create the product data ");

        ProductDto creaeProduct = productServicce.createProduct(productDto);
        logger.info("Complete the request for create the product data ");
        return new ResponseEntity<>(creaeProduct, HttpStatus.CREATED);
    }

    /**
     * @param  productId
     * @return product
     * @auther panchafula
     * @apiNote to search for update poduct
     * */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct( @PathVariable String productId,
                                                     @RequestBody ProductDto productDto){
        logger.info("Entering the request for update the product data with productyId",productId);
        ProductDto product = productServicce.updateProduct(productDto, productId);
        logger.info("Complete the request for update product data with productyId",productId);
        return new ResponseEntity<>(product,HttpStatus.OK);


    }
    /**
     * @param  productId
     * @return response
     * @auther panchafula
     * @apiNote to search for delete product
     * */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponceMessage> deleteProduct(@PathVariable String productId){
        logger.info("Entering the request for delete the product data with productyid{} ");
        productServicce.deleteProduct(productId);
        ApiResponceMessage response = ApiResponceMessage.builder().message("deleted successfully")
                .status(HttpStatus.OK).success(true).build();
        logger.info("Complete the request for delete the product data with productyId");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    /**
     * @param  productId
     * @return productDto
     * @auther panchafula
     * @apiNote to search for get single product
     * */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
        logger.info("Entering the request for get single product data with productyid{} ",productId);
        ProductDto productDto = productServicce.getProduct(productId);
        logger.info("Complete the request for get single product data with productyId",productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    /**
     * @param  pageNumber, pageSize, sortBy, sortDir
     * @return response
     * @auther panchafula
     * @apiNote to search for all product*/
    @GetMapping
    public ResponseEntity<PagebleResponse<ProductDto>> getAllProduct
            (@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
             @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
             @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
        logger.info("Entering the request for get the all Product data ");

        PagebleResponse<ProductDto> response = productServicce.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for get the all Product data ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * @param  pageNumber, pageSize, sortBy, sortDir
     * @return response
     * @auther panchafula
     * @apiNote to search for live product
     * */
    //get All Live
    // /product/live
    @GetMapping("/live")
    public ResponseEntity<PagebleResponse<ProductDto>> getAllLive
            (@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
             @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
             @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
        logger.info("Entering the request for get the all live Product data ");

        PagebleResponse<ProductDto> response = productServicce.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for get the all live Product data ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * @param query, pageNumber, pageSize, sortBy, sortDir
     * @return response
     * @auther panchafula
     * @apiNote to search for product
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PagebleResponse<ProductDto>> searchProduct(@PathVariable String query,
            @RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
             @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
             @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
        logger.info("Entering the request for search Product data ");

        PagebleResponse<ProductDto> response = productServicce.serachByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for search Product data ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return imageResponse
     * @auther panchafula
     * @apiNote to upload image at given productId
     */
    //serve image
    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage
    (@RequestParam("productImage") MultipartFile image,
     @PathVariable String productId) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);
        ProductDto product = productServicce.getProduct(productId);
        product.setProductImageName(imageName);
        ProductDto productDto = productServicce.updateProduct(product, productId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).
                success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity <>(imageResponse,HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @return
     * @auther panchafula
     * @apiNote to serv image at given productId
     * @throws IOException
     */
    @GetMapping(value ="/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto product = productServicce.getProduct(productId);
        logger.info("product image name : {}",product.getProductImageName());
        InputStream resource =fileService.getResource(imageUploadPath,product.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
