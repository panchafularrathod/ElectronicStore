package com.bikkadIt.electronicstore.ElectronicStore.service.impl;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CretateOrderRequst;
import com.bikkadIt.electronicstore.ElectronicStore.dto.OrderDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.*;
import com.bikkadIt.electronicstore.ElectronicStore.exception.BadApiRequeast;
import com.bikkadIt.electronicstore.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.electronicstore.ElectronicStore.helper.Helper;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.CartRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.OrderRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.UserRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    private CartRepository cartRepository;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Override
    public OrderDto createOrder(CretateOrderRequst orderDto ) {
        logger.info("Initiating the dao call for the create order.............");
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        // fetch user
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));

        // fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.size()<=0){
            throw new BadApiRequeast("invalid number of items in cart !!");
        }

        // other check
        Order order = Order.builder().billingName(orderDto.getBillingName())
                .orderDate(new Date())
                .orderStatus(orderDto.getOrderStatus())
                .billingAddress(orderDto.getBillingAddress())
                .deliveryDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .billingPhone(orderDto.getBillingPhone())
                .orderId(UUID.randomUUID().toString())
                .user(user).build();
        //orderIde

        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

            //convert cartItem in order Item
            OrderItem.builder().quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountPrice())
                    .order(order).build();

            return new OrderItem();
        }).collect(Collectors.toList());

        order.setOrdersItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        // cart clear
        cart.getItems().clear();
        cartRepository.save(cart);
        Order saveOrder = orderRepository.save(order);
        logger.info("complete the dao call for create order.......");
        return modelMapper.map(saveOrder,OrderDto.class);
        
    }

    @Override
    public void removeOrder(String orderId) {
        logger.info("Intiating the dao call for remove order with the userId{}:......");
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));

        orderRepository.delete(order);
        logger.info("complete the dao call for remove order data with the userId{}:......");
    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        logger.info("Intiating the dao call for get order of user with userId{}:......");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        List<OrderDto> orders= orderRepository.findByUser(user );

        List<OrderDto> orderdtos = orders.stream().map(order ->
                modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        logger.info("complete the dao call for get order of user.......");
        return orderdtos;
    }

    @Override
    public PagebleResponse<OrderDto> getOrder(int pagesize, int pageNumber, String sortBy, String sortDir) {
        logger.info("Intiating the dao call for get all order :......");
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?
                (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pagesize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        logger.info("complete the dao call for get All order .......");
        return Helper.getPagebleResponse(page, OrderDto.class);
    }
}
