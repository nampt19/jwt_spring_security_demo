package com.example.jwt_spring_security.controller;

import com.example.jwt_spring_security.entity.CustomUserDetails;
import com.example.jwt_spring_security.entity.User;
import com.example.jwt_spring_security.jwt.JwtTokenProvider;
import com.example.jwt_spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername());

        if (user!=null && passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));
            } catch (DisabledException e) {
                throw new Exception("USER_DISABLED", e);
            } catch (BadCredentialsException e) {
                throw new Exception("INVALID_CREDENTIALS", e);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return new LoginResponse("Bearer " + jwt);
        } else {
            return new LoginResponse("invalid user");
        }


    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public String randomStuff() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "JWT Hợp lệ mới có thể thấy được message này: \n"
                + ((CustomUserDetails) auth.getPrincipal()).getUsername() + "\n"
                + ((CustomUserDetails) auth.getPrincipal()).getPassword() + "\n"
                + ((CustomUserDetails) auth.getPrincipal()).getAuthorities();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return auth.getName() + " logout !";
    }

}
