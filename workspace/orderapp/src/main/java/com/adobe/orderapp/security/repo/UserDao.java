package com.adobe.orderapp.security.repo;


import com.adobe.orderapp.security.entity.User;
import com.adobe.orderapp.security.service.UserDetailsServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
