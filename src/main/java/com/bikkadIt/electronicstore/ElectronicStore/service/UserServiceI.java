package com.bikkadIt.electronicstore.ElectronicStore.service;

import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;

import java.util.List;

public interface UserServiceI {

    //create

    UserDto createUser(UserDto userDto);

    //update

    UserDto updateUser(UserDto userDto,String userid);

    //delete

    void deleteUser(String userid);

    //get all user

   PagebleResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single user

    UserDto getUserById(String userid);

    // get single user by email

    UserDto getUserByEmail(String email);

    // search user

    List<UserDto> searchUser(String keyword);
}


