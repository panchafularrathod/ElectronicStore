package com.bikkadIt.electronicstore.ElectronicStore.dto;

import com.bikkadIt.electronicstore.ElectronicStore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

        private String userid;

        @Size(min=3, max=20,message="Invalid name....")
        private String name;
        @Email(message = "Invalid Email")
        @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid user Email")
        private String email;
        @NotBlank(message = "Password is reqaired")
        private String password;
        @Size(min=4, max=6,message="invalid gender")
        private String gender;

        private String imageName;
        @NotBlank(message = "Write something")
        private String about;
    }


