package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.config.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.service.UserServiceI;
import com.bikkadIt.electronicstore.ElectronicStore.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

@Autowired
        private UserServiceI userServiceI;

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * @author panchafula
     * @apiNote  save user data in database
     * @since v1.8
     * @param userDto
     * @return save user
     */
        //create
        @PostMapping("/")
        public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
            logger.info("Entering the requestfor create userdata...");
            UserDto CreateUserDto = this.userServiceI.createUser(userDto);
            logger.info("complete the request for create user data");
            return new ResponseEntity<>(CreateUserDto, HttpStatus.CREATED);
        }
    /**
     * @author panchafula
     * @apiNote delete user
     * @since v1.8
     * @param userid
     * @return delete user
     */
        // detele
        @DeleteMapping("/{userId}")
        public ResponseEntity<String>deleteUser(@Valid @PathVariable("userId") String userid) {
            logger.info("Entering the request for delete the user data with userid{} ");
            this.userServiceI.deleteUser(userid);
            logger.info("Complete the request for delete the user data with userId");
            return new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
        }
    /**
     * @author panchafula
     * @apiNote updateUser
     * @since v1.8
     * @param userDto
     * @return updated user
     */
        //update
        @PutMapping("/userId")
        public ResponseEntity<UserDto> updateUser(@PathVariable("userid") String userId,
                                                  @RequestBody UserDto userDto ) {
            logger.info("Entering the request for update the user data with userid{}",userId);
            UserDto updateUser = this.userServiceI.updateUser(userDto, userId);
            logger.info("Complete the request for update the user data with userid{}",userId);
            return new ResponseEntity<>(updateUser,HttpStatus.OK);
        }
    /**
     * @author panchafula
     * @apiNote get All user
     * @since v1.8
     * @param pageSize,pagenumber
     * @return userList
     */
        // get all users
        @GetMapping("/")
        public ResponseEntity <PagebleResponse<UserDto>> getAllUser(@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
                                                                   @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
            logger.info("Entering the request for get the all user data ");
            logger.info("Complete the request for get the all user data ");
            return new ResponseEntity<>(userServiceI.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);

        }
    /**
     * @author panchafula
     * @apiNote getuser
     * @since v1.8
     * @param userid
     * @return userid
     */
        // get single user
        @GetMapping("/{userId}")
        public ResponseEntity<UserDto> getUser(@PathVariable String userid){
            logger.info("Entering the request for get single user data with userid{}",userid);
            logger.info("Complete the request for get all user data with userid{}",userid);
            return new ResponseEntity<>(userServiceI.getUserById(userid),HttpStatus.OK);

        }

    /**
     * @author panchafula
     * @apiNote get user using email
     * @since v1.8
     * @param email
     * @return user by email
     */
        // get by email
        @GetMapping("/email/{email}")
        public ResponseEntity<UserDto> getUserbyEmail(@PathVariable String email){
            logger.info("Entering the request for get  user data with email{}");
            logger.info("Complete the request for get  user data with email{}");
            return new ResponseEntity<>(userServiceI.getUserByEmail(email),HttpStatus.OK);
        }

    /**
     * @author panchafula
     * @apiNote search user from database
     * @since v1.8
     * @param keyword
     * @return user keyword
     */
        //search user
        @GetMapping("/search/{keyword}")
        public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
            logger.info("Entering the request for serach user data with keyword");
            logger.info("Complete the request for search user data with Keyword");
            return new ResponseEntity<>(userServiceI.searchUser(keyword),HttpStatus.OK);
        }
    }


