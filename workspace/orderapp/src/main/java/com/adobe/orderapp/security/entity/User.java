package com.adobe.orderapp.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="users")
public class User implements UserDetails  {
    @Id
    private String email;

    private String username;

    private String password;
    // extra fields

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="USERS_ROLES",
         joinColumns = {
            @JoinColumn(name="USER_ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID")}
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach((role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }));
        return authorities;
    }
}
