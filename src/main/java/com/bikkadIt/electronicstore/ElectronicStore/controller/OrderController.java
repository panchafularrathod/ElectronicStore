package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CretateOrderRequst;
import com.bikkadIt.electronicstore.ElectronicStore.dto.OrderDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    /**
     * @author panchafula
     * @apiNote  save order data in database
     * @since v1.8
     * @param orderDto
     * @return save order
     */
    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CretateOrderRequst orderDto) {
        logger.info("Entering the request for create order data ");
        OrderDto order = orderService.createOrder(orderDto);
        logger.info("Complete the request for create order  data ");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    /**
     * @author panchafula
     * @apiNote remove order
     * @since v1.8
     * @param orderId
     * @return remove order
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponceMessage> remove(@PathVariable String orderId){
        logger.info("Entering the request for order form data {}"+orderId);
        orderService.removeOrder(orderId);
        ApiResponceMessage order_is_removed = ApiResponceMessage.builder().
                status(HttpStatus.OK).message("order is removed").success(true).build();
        logger.info("Complete the request for remove order from data {}"+orderId);
        return new ResponseEntity<>(order_is_removed,HttpStatus.OK);
    }
    /**
     * @author panchafula
     * @apiNote get order By user
     * @since v1.8
     * @param userId
     * @return order of user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable String userId){
        logger.info("Entering the request for get order by user data {userId}"+userId);
        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
        logger.info("Complete the request for get order by user data {userId}"+userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }
    /**
     * @author panchafula
     * @apiNote get all order
     * @since v1.8
     * @param pageSize, pageNumber, sortBy, sortDir
     * @return orders
     */
    @GetMapping("/")
    public ResponseEntity<PagebleResponse<OrderDto>>getOrder(@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                                                             @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
                                                             @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
                                                             @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir) {
        logger.info("Entering the request for get the all order data ");
            PagebleResponse<OrderDto> orders = orderService.getOrder(pageSize, pageNumber, sortBy, sortDir);
        logger.info("Complete the request for get the all order data ");
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }
