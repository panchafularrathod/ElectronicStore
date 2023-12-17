package com.bikkadIt.electronicstore.ElectronicStore.repository;

import com.bikkadIt.electronicstore.ElectronicStore.entities.Cart;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart>findByUser(User user);
}
