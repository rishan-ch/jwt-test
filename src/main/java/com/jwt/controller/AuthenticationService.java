package com.jwt.controller;

import com.jwt.config.JwtService;
import com.jwt.model.Role;
import com.jwt.model.User;
import com.jwt.repositoty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.user);

        userRepo.save(user);

        String token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build();

    }

    public AuthenticationResponse authenticate(RegisterRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = userRepo.findByEmail(req.getEmail());

        String token = jwtService.generateToken(user);
        System.out.println("tttttttttttttttttoken 2 ==== " + token);
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
