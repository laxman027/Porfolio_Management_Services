package com.portfolio.portfoliomanagementservices.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliomanagementservices.model.AuthRequest;
import com.portfolio.portfoliomanagementservices.model.User;
import com.portfolio.portfoliomanagementservices.repository.UserRepository;
import com.portfolio.portfoliomanagementservices.security.CustomUserDetails;
import com.portfolio.portfoliomanagementservices.security.JwtService;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        Optional<User> existing = userRepo.findByUsername(user.getUsername());
        if (existing.isPresent()) return ResponseEntity.status(409).body("Username already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    	@PostMapping("/login")
    	public ResponseEntity<String> login(@RequestBody AuthRequest request) {
    	    try {
    	        // 🔐 Let Spring Security do the authentication
    	       Authentication authentication = authManager.authenticate( new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

    	        // ✅ Authentication success — generate JWT
    	       CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal(); // only if your User implements UserDetails
    	       User user=customUserDetails.getUser();
    	        String token = jwtService.generateToken(user);

    	        return ResponseEntity.ok("Login Successfully ."+token);
    	    } catch (AuthenticationException e) {
    	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    	    }
    	}
}