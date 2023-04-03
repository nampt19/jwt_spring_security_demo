package com.example.jwt_spring_security;

import com.example.jwt_spring_security.entity.User;
import com.example.jwt_spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JwtSpringSecurityApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Khi chương trình chạy
        // Insert vào csdl một user.
        User user1 = new User();
        user1.setUsername("loda");
        user1.setPassword(passwordEncoder.encode("123"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("hoho");
        user2.setPassword(passwordEncoder.encode("123"));
        userRepository.save(user2);

        System.out.println(user1 + "\n" + user2);
    }
}
