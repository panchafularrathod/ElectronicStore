package com.bikkadIt.electronicstore.ElectronicStore.service.impl;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.AddItemToCartRequest;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CartDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Cart;
import com.bikkadIt.electronicstore.ElectronicStore.entities.CartItem;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import com.bikkadIt.electronicstore.ElectronicStore.exception.BadApiRequeast;
import com.bikkadIt.electronicstore.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.electronicstore.ElectronicStore.repository.CartItemRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.CartRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.ProductRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.UserRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();
        if (quantity<=0){
            throw new BadApiRequeast("requested quantity is not valid");
        }
// fetch product
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        // fetch user from db
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        Cart cart = null;
                try{
               cart= cartRepository.findByUser(user).get();}
                catch(NoSuchElementException e){

                    cart= new Cart();
                    cart.setCartId(UUID.randomUUID().toString());
                    cart.setCreateAt(new Date());
                }

                //perform cart opration
   // cart already present then update
       // boolean updated=false;
        AtomicReference<Boolean> updated=new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
         List<CartItem> updatedItem= items.stream().map(item ->{
              if (item.getProduct().getProductId().equals(productId)){
                 // item already present in cart
                  item.setQuantity(quantity);
                  item.setTotalPrice(quantity*product.getPrice());
                  updated.set(true);
              }
                  return item;

          }).collect(Collectors.toList());

         cart.setItems(updatedItem);

         // create Items
       if (!updated.get()){
        CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getPrice())
                .cart(cart).product(product).build();
        cart.getItems().add(cartItem);}

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {

        User user1 = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        Cart cart = cartRepository.findByUser(user1).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        cart.getItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {

        User user1 = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        Cart cart = cartRepository.findByUser(user1).orElseThrow(() ->
                new ResourceNotFoundException(AppConstant.NOT_FOUND));
        return mapper.map(cart,CartDto.class);
    }
}
