package com.example.jwt_spring_security.repository;

import com.example.jwt_spring_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String name);
    User findByUsernameAndPassword(String username,String password);
}
