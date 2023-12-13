package com.bikkadIt.electronicstore.ElectronicStore.service;

import com.bikkadIt.electronicstore.ElectronicStore.dto.CategoryDto;
import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Role;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.CategoryRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.RoleRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    CategoryServiceImpl categoryService;
    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;
    Category category;


    @BeforeEach
    public void init(){

        category=Category.builder().title("laptop").discription("this category contain laptop relate product").coverImage("abc.png").build();

    }
    @Test
    public void createUserTest(){

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto categoryDto = categoryService.create(mapper.map(category, CategoryDto.class));

        System.out.println(categoryDto.getTitle());
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals("Laptop", categoryDto.getTitle());

    }
    @Test
    public void updateUser_Test(){
        String categoryId="defg";
        CategoryDto categoryDto=CategoryDto.builder().title("mobile").coverImage("xtr.png").build();


        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        // UserDto updatedUser=mapper.map(user, UserDto.class);
        System.out.println(updateCategory.getTitle());
        System.out.println(updateCategory.getCoverImage());
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(categoryDto.getTitle(),updateCategory.getTitle(),"tital is not valid");}


    @Test
    public void deleteCategoryTest(){
        String categoryId="categoryidcvb";
        Mockito.when(categoryRepository.findById("categorycvb")).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);


    }
    public void getAllUserTest(){

        Category category1=Category.builder()
                .title("Refrigerator")
                .discription( "this category contain mobile related product")
                .coverImage("mob.png").build();;

        List<Category> categoryList= Arrays.asList(category1, category);
        Page<Category> page = new PageImpl<>(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        // Sort sort= Sort.by("name").ascending();
        // Pageable pageble= PageRequest.of(1,2,sort);
        PagebleResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 2, "name", "asc");

        Assertions.assertEquals(2,allCategory.getContent().size());


    }

}
