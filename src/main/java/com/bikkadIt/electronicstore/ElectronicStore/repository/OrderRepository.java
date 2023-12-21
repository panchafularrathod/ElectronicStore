package com.bikkadIt.electronicstore.ElectronicStore.repository;

import com.bikkadIt.electronicstore.ElectronicStore.dto.OrderDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Order;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {



}
