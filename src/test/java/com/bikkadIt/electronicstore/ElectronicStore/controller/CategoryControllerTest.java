package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.dto.CategoryDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {




        @MockBean
        private CategoryService categoryService;
        @Autowired
        private CategoryController categoryController;
        @Autowired
        private ModelMapper modelMapper;
        @Autowired
        private MockMvc mockMvc;

        private Category category;

        @BeforeEach
        public void init() {
            category = Category.builder().id(UUID.randomUUID().toString()).title("mobile")
                    .discription("this category contain mobile related product").coverImage("mob.png").build();
        }

        @Test
        public void createCategoryTest() throws Exception {
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            Mockito.when(categoryService.create(Mockito.any())).thenReturn(categoryDto);
            this.mockMvc.perform(MockMvcRequestBuilders.post("/api/category").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonString(category)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title").exists());
        }

        private String convertObjectToJsonString(Object category) throws Exception {
            try {
                return new ObjectMapper().writeValueAsString(category);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Test
        public void updateCategoryTest() throws Exception {
            String categoryId = category.getId();
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(categoryDto);
            this.mockMvc.perform(MockMvcRequestBuilders.put("/api/category/" + categoryId).contentType(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonString(category)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").exists());
        }
        @Test
        public void getAllCategoryTest() throws Exception {
            CategoryDto dto1 = CategoryDto.builder().title("Tv").discription("this is anroid Tv of Haier company").coverImage("tv.png").build();
            CategoryDto dto2 = CategoryDto.builder().title("Refrigerator").discription("this is anroid Tv of Haier company").coverImage("tv.png").build();
            CategoryDto dto3 = CategoryDto.builder().title("Cooler").discription("this is anroid Tv of Haier company").coverImage("tv.png").build();
            CategoryDto dto4 = CategoryDto.builder().title("mobilephone").discription("this is anroid Tv of Haier company").coverImage("tv.png").build();
            PagebleResponse<CategoryDto> pageableResponse = new PagebleResponse<>();
            pageableResponse.setContent(Arrays.asList(dto1, dto2, dto3, dto4));
            pageableResponse.setLastPage(false);
            pageableResponse.setPageSize(10);
            pageableResponse.setPageNumber(100);
            pageableResponse.setTotalElements(1000l);
            Mockito.when(categoryService.getAllCategory(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
        @Test
        public void getCategoryByIdTest() throws Exception {
            String categoryId = category.getId();
            CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
            Mockito.when(categoryService.getCategory(Mockito.anyString())).thenReturn(categoryDto);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/"+categoryId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonString(category))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").exists());
        }
        @Test
        public void deleteCategoryTest() throws Exception {
            String categoryId="hgdghhkf";
            doNothing().when(categoryService).deleteCategory(categoryId);
            ResponseEntity<ApiResponceMessage> apiResponse = categoryController.deleteCategory(categoryId);
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/"+categoryId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            String message = apiResponse.getBody().getMessage();
            Assertions.assertEquals("category deleted successfully", message);
        }
    }


