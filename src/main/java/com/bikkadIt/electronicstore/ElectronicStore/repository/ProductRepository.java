package com.bikkadIt.electronicstore.ElectronicStore.repository;

import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByTitleContaining(String subtitle, Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
    Page<Product> findByCategory(Category category, Pageable pageable);
}
