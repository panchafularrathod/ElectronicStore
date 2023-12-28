package com.bikkadIt.electronicstore.ElectronicStore.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails {


        @Id
        // @GeneratedValue(strategy = GenerationType.IDENTITY )
        private String userid;

        @Column(name="user_name")
        private String name;

        @Column(name = "user_email", unique = true)
        private String email;

        @Column(name = "user_password",length = 10)
        private String password;

        private String gender;

        @Column(length = 1000)
        private String about;

        @Column(name="user_imageimagename")
        private String imageName;

        private String roles;


        @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
        private List<Order> orders = new ArrayList<>();

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
        }

        @Override
        public String getUsername() {
                return this.email;
        }

        @Override
        public String getPassword(){

                return this.password;
        }
        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}


