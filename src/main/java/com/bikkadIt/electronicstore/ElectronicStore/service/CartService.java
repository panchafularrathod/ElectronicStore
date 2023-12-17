package com.bikkadIt.electronicstore.ElectronicStore.service;

import com.bikkadIt.electronicstore.ElectronicStore.dto.AddItemToCartRequest;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CartDto;

public interface CartService {

    // add item to cart
    // case1 cart for user not available we create the cart and then aad the item
    // case 2 cart available then add the item to  cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    // remove item from cart

    void removeItemFromCart(String userId, int cartItme);

 //   remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
