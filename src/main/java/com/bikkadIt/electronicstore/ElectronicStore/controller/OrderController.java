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
    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CretateOrderRequst orderDto) {
        OrderDto order = orderService.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponceMessage> remove(@PathVariable String orderId){

        orderService.removeOrder(orderId);
        ApiResponceMessage order_is_removed = ApiResponceMessage.builder().
                status(HttpStatus.OK).message("order is removed").success(true).build();
        return new ResponseEntity<>(order_is_removed,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable String userId){
        logger.info("Entering the request for get order by user data ");
        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }
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
