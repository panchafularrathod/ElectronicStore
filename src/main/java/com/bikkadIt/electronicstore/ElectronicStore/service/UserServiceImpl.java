package com.bikkadIt.electronicstore.ElectronicStore.service;

import com.bikkadIt.electronicstore.ElectronicStore.config.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import com.bikkadIt.electronicstore.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.electronicstore.ElectronicStore.helper.Helper;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI{
        @Autowired
        private UserRepository userRepository;
           @Autowired
        private ModelMapper modelMapper;

           private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

        public UserDto createUser(UserDto userDto) {
            logger.info("Intiating the dao call for the create user.............");
            String userId= UUID.randomUUID().toString();
            userDto.setUserid(userId);
            User user = this.modelMapper.map(userDto, User.class);
            User savedUser= userRepository.save(user);
            UserDto userDto1 = this.modelMapper.map(savedUser, UserDto.class);
            logger.info("complete the dao call for create user.......");
            return userDto1;
        }

        @Override
        public UserDto updateUser(UserDto userDto, String userid) {

            logger.info("Intiating the dao call for update the user data with the userId{}:......",userid);

            User user= (User) this.userRepository.findById(userid)
                    .orElseThrow(()-> new ResourceNotFoundException(  AppConstant.NOT_FOUND));

            user.setUserid(userDto.getUserid());
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setAbout(userDto.getAbout());
            user.setImageName(userDto.getImageName());
            User save = this.userRepository.save(user);

            logger.info("complete the dao call for update the user data with the userId{}:......",userid);
            return this.modelMapper.map(save, UserDto.class);
        }

        @Override
        public void deleteUser(String userid) {


            logger.info("Intiating the dao call for delete the user data with the userId{}:......");
            User user = (User) userRepository.findById(userid).orElseThrow( ()->
                    new ResourceNotFoundException(AppConstant.NOT_FOUND));
            userRepository.delete(user);
            logger.info("complete the dao call for delete the user data with the userId{}:......");
        }

        @Override
        public PagebleResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

            logger.info("Intiating the dao call for get the all  user data ");
            Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
            Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
            Page<User> page = userRepository.findAll(pageable);
            PagebleResponse<UserDto> response = Helper.getPagebleResponse(page, UserDto.class);

            logger.info("complete the dao call for get the all user data ");
            return response ;
        }

        @Override
        public UserDto getUserById(String userid) {

            logger.info("Intiating the dao call for get the singl user data with the userId{}:......",userid);
            User user = (User) userRepository.findById(userid).orElseThrow(() ->
                    new ResourceNotFoundException(AppConstant.NOT_FOUND));

            logger.info("complete the dao call for get the single user data with the userId{}:......",userid);

            return this.modelMapper.map(user, UserDto.class);
        }

        @Override
        public UserDto getUserByEmail(String email) {

            logger.info("Intiating the dao call for get the single user data with the email{}:......");
            User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new ResourceNotFoundException(AppConstant.NOT_FOUND));
            logger.info("complete the dao call for get the single user data with the email{}:......");
            return this.modelMapper.map(user,UserDto.class);
        }

        @Override
        public List<UserDto> searchUser(String keyword) {

            logger.info("Intiating the dao call for search the user data with the keyword......");

            List<User> users = userRepository.findByNameContaining(keyword);
            List<UserDto> dtoList = users.stream().map(user ->
                    this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
            logger.info("complete the dao call for search the user data with the keyword......");
            return  dtoList;
        }
    }


