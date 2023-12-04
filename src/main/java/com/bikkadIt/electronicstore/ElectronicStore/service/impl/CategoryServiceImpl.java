package com.bikkadIt.electronicstore.ElectronicStore.service.impl;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.CategoryDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Category;
import com.bikkadIt.electronicstore.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.electronicstore.ElectronicStore.helper.Helper;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.CategoryRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;



@Service

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    private String coverimagePath;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        // create random category id
        String categoryId= UUID.randomUUID().toString();
        categoryDto.setId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        logger.info("Initiating the dao call to save catergory data");
        Category saveCategory = categoryRepository.save(category);
        logger.info("Completed the dao call to save category data");
        return mapper.map(saveCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating the dao call to update data with categoryId:{}", categoryId);
        // get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDiscription(categoryDto.getDiscription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updateCategory = categoryRepository.save(category);
        logger.info("Completed the dao call to update data with categoryId:{}", categoryId);
        return mapper.map(updateCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Initiating dao call to deleted data with categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        String path = coverimagePath + category.getCoverImage();
        try {
            Path path1 = Paths.get(path);
            Files.delete(path1);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        logger.info("completed dao call to deleted data with categoryId:{}", categoryId);
        categoryRepository.delete(category);

    }

    @Override
    public PagebleResponse<CategoryDto> getAllCategory(int pageNumber,
                                                       int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?
                (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        logger.info("Initiating dao call to retrived all data");

        Page<Category> page=categoryRepository.findAll(pageable);

        PagebleResponse<CategoryDto> pagebleResponse = Helper.getPagebleResponse(page, CategoryDto.class);
        logger.info("Completed dao call to retrived all data");
        return pagebleResponse;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        logger.info("Initiating the dao call for retrived data with categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        logger.info("Completed the dao call to retrived data with categoryId:{}", categoryId);
        return mapper.map(category,CategoryDto.class);
    }
}
