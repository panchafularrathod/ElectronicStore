package com.bikkadIt.electronicstore.ElectronicStore.service;


import com.bikkadIt.electronicstore.ElectronicStore.dto.CretateOrderRequst;
import com.bikkadIt.electronicstore.ElectronicStore.dto.OrderDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CretateOrderRequst orderDto);

    //remove order

    void removeOrder(String orderId);
    //get orders of user
    List<OrderDto> getOrderOfUser(String userId);

    // order  get

    PagebleResponse<OrderDto> getOrder(int pagesize, int pageNumber, String sortBy, String sortDir);

    //order apis related to order

}
