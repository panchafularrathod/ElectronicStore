package com.bikkadIt.electronicstore.ElectronicStore.service;


import com.bikkadIt.electronicstore.ElectronicStore.dto.OrderDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(OrderDto orderDto, String userId, String cartId);
    //remove order

    void removeOrder(String userId);
    //get orders of user
    List<OrderDto> getOrderOfUser(String userId);

    // order  get

    PagebleResponse<OrderDto> getOrder(int pagesize, int pageNumber, String sortBy, String sortDir);

    //order apis related to order

}
