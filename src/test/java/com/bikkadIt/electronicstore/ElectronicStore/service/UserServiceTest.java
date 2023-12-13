package com.bikkadIt.electronicstore.ElectronicStore.service;


import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.Role;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.RoleRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.UserRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;
    User user;
    Role role;
    String roleId;

    @BeforeEach
    public void init(){
        role=Role.builder().roleId("abc").roleName("NORMAL").build();
         user=User.builder().name("Chiku").email("chiku@gmail.com").about("Engineer")
        .gender("male").imageName("abc.png").password("chiku*123").build();

         roleId="abc";


    }

    @Test
    public void createUserTest(){

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
                Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));

        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Chiku", user1.getName());

    }
    @Test
    public void updateUser_Test(){
      String userId="abcdf";
       UserDto userDto=UserDto.builder().name("Chiku Rathod").about("Engineer")
                .gender("male").imageName("xtr.png").build();


       Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
       Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updateUser = userService.updateUser(userDto, userId);
       // UserDto updatedUser=mapper.map(user, UserDto.class);
        System.out.println(updateUser.getName());
        System.out.println(updateUser.getImageName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name is not valid");}
        @Test
        public void deleteUserTest(){
        String userId="useridcvb";
        Mockito.when(userRepository.findById("useridcvb")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);


        }

        public void getAllUserTest(){

         User   user1=User.builder().name("Chiku Rathod").email("chiku@gmail.com").about("Engineer")
                    .gender("male").imageName("abc.png").password("chiku*123").build();

            List<User> userList= Arrays.asList(user, user1);
        Page<User> page = new PageImpl<>(userList);
            Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
       // Sort sort= Sort.by("name").ascending();
       // Pageable pageble= PageRequest.of(1,2,sort);
        PagebleResponse<UserDto> allUser = userService.getAllUser(1,2,"name","asc");

        Assertions.assertEquals(2,allUser.getContent().size());


    }
}
