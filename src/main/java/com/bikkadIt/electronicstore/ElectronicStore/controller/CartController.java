package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.dto.AddItemToCartRequest;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CartDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import com.bikkadIt.electronicstore.ElectronicStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    // add item to cart
@PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId,
                                                 @RequestBody AddItemToCartRequest request){

        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }
    @DeleteMapping("/{userid}/{itemId}")
    public ResponseEntity<ApiResponceMessage> removeItemFromCart(@PathVariable String userId, int cartItem){

    cartService.removeItemFromCart(userId,cartItem);
        ApiResponceMessage respone = ApiResponceMessage.builder().message("Item is remove..")
                .success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(respone, HttpStatus.OK);


    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<ApiResponceMessage> clearCart(@PathVariable String userId){

        cartService.clearCart(userId);
        ApiResponceMessage respone = ApiResponceMessage.builder().message("cart is clear")
                .success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(respone, HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){

        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }
}
