package com.bikkadIt.electronicstore.ElectronicStore.helper;

import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <V,U>PagebleResponse<V> getPagebleResponse(Page<U> page ,Class<V>type){
        List<U> entity=page.getContent();
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
        PagebleResponse<V> response= new PagebleResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

return response;
    }
}
