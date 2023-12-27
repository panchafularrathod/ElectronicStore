package com.bikkadIt.electronicstore.ElectronicStore.service.impl;

import com.bikkadIt.electronicstore.ElectronicStore.Constant.AppConstant;
import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.entities.User;
import com.bikkadIt.electronicstore.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.electronicstore.ElectronicStore.helper.Helper;
import com.bikkadIt.electronicstore.ElectronicStore.payload.PagebleResponse;
import com.bikkadIt.electronicstore.ElectronicStore.repository.RoleRepository;
import com.bikkadIt.electronicstore.ElectronicStore.repository.UserRepository;
import com.bikkadIt.electronicstore.ElectronicStore.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {
        @Autowired
        private UserRepository userRepository;
           @Autowired
        private ModelMapper modelMapper;
           private RoleRepository roleRepository;

    @Value("${user.profile.image.path}")

           private String imagePath;

           private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

        public UserDto createUser(UserDto userDto) {
            logger.info("Initiating the dao call for the create user.............");

            //generate uniue id in  String format
            String userId= UUID.randomUUID().toString();
            userDto.setUserid(userId);

            //encoding password
           // userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = this.modelMapper.map(userDto, User.class);

            //fetch role of normal and set  it to user
          // Role role =roleRepository.findById(normalRoleId).get();
           // user.getRoles().add(role);
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

            //delete user profile image
          // images/users/abc.png
            String fullPath = imagePath + user.getImageName();
            try {
            Path path= Paths.get(fullPath);
            Files.delete(path);}
            catch(NoSuchFileException ex){
                logger.info("User image not found in folder");
                ex.printStackTrace();
            }catch (IOException ex){
                 ex.printStackTrace();
            }
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


