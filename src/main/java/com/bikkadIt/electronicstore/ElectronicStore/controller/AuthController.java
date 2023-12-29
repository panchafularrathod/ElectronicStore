package com.bikkadIt.electronicstore.ElectronicStore.controller;

import com.bikkadIt.electronicstore.ElectronicStore.dto.JwtRequest;
import com.bikkadIt.electronicstore.ElectronicStore.dto.JwtResponse;
import com.bikkadIt.electronicstore.ElectronicStore.dto.UserDto;
import com.bikkadIt.electronicstore.ElectronicStore.sequrity.JWTHelper;
import com.bikkadIt.electronicstore.ElectronicStore.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private JWTHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest jwtRequest){
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.helper.generateToken(userDetails);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder()
                .jwtToke(token)
                .user(userDto).build();
        return new ResponseEntity<>(response,HttpStatus.OK);



    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authenticationToken= new
                UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException exception){

            throw new BadCredentialsException("Invalid Username or password ");

        }
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){

        String name = principal.getName();
        UserDto userDto = this.modelMapper.map(userDetailsService.loadUserByUsername(name), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
