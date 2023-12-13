package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import com.bikkadIt.electronicstore.ElectronicStore.payload.ApiResponceMessage;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.service.UserServiceI;
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
public class UserControllerTest {
         @MockBean
        private UserServiceI userService;
        @Autowired
        private UserController userController;
        @Autowired
        private ModelMapper modelMapper;
        @Autowired
        private MockMvc mockMvc;

        private User user;
        @BeforeEach
        public void init(){
            user= User.builder()
                    .userid(UUID.randomUUID().toString())
                    .name("Prachi Parihar")
                    .email("prachi@gamil.com")
                    .password("Pra@2346")
                    .gender("female")
                    .imageName("abc.png")
                    .about("I am java devloper and worked as a full stack devloper")
                    .build();
        }
        @Test
        public void createUserTest() throws Exception {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
            this.mockMvc.perform(MockMvcRequestBuilders.post("/apis/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonString(user))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").exists());
        }
        private String convertObjectToJsonString(Object user){
            try{
                return new ObjectMapper().writeValueAsString(user);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Test
        public void updateUserTest() throws Exception {
            String userId= user.getUserid();
            UserDto userDto = modelMapper.map(user, UserDto.class);
            Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);
            this.mockMvc.perform(MockMvcRequestBuilders.put("/apis/user/userId/"+userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonString(user))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").exists());
        }
        @Test
        public void getUserByIdTest() throws Exception {
            String userId= user.getUserid();
            UserDto userDto = this.modelMapper.map(user, UserDto.class);
            Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(userDto);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/apis/user/userId/"+userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonString(user))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").exists());
        }
        @Test
        public void getAllUserTest() throws Exception {
            UserDto obj1 = UserDto.builder().name("shree").email("Shree@gmail.com").password("Shree@123").about("Testing").build();
            UserDto obj2 = UserDto.builder().name("Renu").email("Shree@gmail.com").password("Shree@123").about("Testing").build();
            UserDto obj3 = UserDto.builder().name("prachi").email("Shree@gmail.com").password("Shree@123").about("Testing").build();
            UserDto obj4 = UserDto.builder().name("Anu").email("Shree@gmail.com").password("Shree@123").about("Testing").build();
            PagebleResponse<UserDto> pageableResponse= new PagebleResponse<>();
            pageableResponse.setContent(Arrays.asList(obj1,obj2,obj3,obj4));
            pageableResponse.setLastPage(false);
            pageableResponse.setPageSize(10);
            pageableResponse.setPageNumber(100);
            pageableResponse.setTotalElements(1000l);
            Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/apis/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
        @Test
        public void deleteUserTest() throws Exception {
            String userId="123";
            doNothing().when(userService).deleteUser(userId);
            ResponseEntity<ApiResponceMessage> response = userController.deleteUser(userId);
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/apis/user/userId/"+userId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            String message = response.getBody().getMessage();
            Assertions.assertEquals("User is deleted successfully", message);
        }
        @Test
        public void getUserByEmailTest() throws Exception {
            String userEmail= user.getEmail();
            UserDto userDto = this.modelMapper.map(user, UserDto.class);
            Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/apis/user/email/"+userEmail)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonString(user))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").exists());
        }

    }



