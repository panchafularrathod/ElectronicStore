package com.bikkadIt.electronicstore.ElectronicStore.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


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

        @Column(name = "user_password",length = 1000)
        private String password;

        private String gender;

        @Column(length = 1000)
        private String about;

        @Column(name="user_imageimagename")
        private String imageName;
      // @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
      // @JoinTable(name = "users_roles",joinColumns =@JoinColumn(name = "user", referencedColumnName = "id"),
      // inverseJoinColumns = @JoinColumn(name = "roles",referencedColumnName = "id"))
     // @ManyToOne
     // @JoinColumn(name = "role_id")
      //  private Role role;


        @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
        private List<Order> orders = new ArrayList<>();

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
           // List<SimpleGrantedAuthority> authorityList = this.roles.stream().map(role ->
                  //  new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
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


