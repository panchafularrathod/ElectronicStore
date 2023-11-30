package com.bikkadIt.electronicstore.ElectronicStore.repository;

import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}
