package com.hommies.userauthwithjwt.service;


import com.hommies.userauthwithjwt.config.JwtService;
import com.hommies.userauthwithjwt.dto.AuthResponse;
import com.hommies.userauthwithjwt.dto.RegisterRequest;
import com.hommies.userauthwithjwt.dto.SignInRequest;
import com.hommies.userauthwithjwt.model.Role;
import com.hommies.userauthwithjwt.model.User;
import com.hommies.userauthwithjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest registerRequest){
        Optional<User> userOptional = userRepository.findByEmail(registerRequest.getEmail());
        if(userOptional.isPresent()){
            throw new RuntimeException("User already exist");
        }
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                // update
               //.role(Role.USER)
               .role(registerRequest.getRole())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        String token = jwtService.generateToken(userRepository.save(user));
       return AuthResponse.builder().
               token(token).
               build();
    }


    public AuthResponse login(SignInRequest signIn){

        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                signIn.getEmail(),
                signIn.getPassword()
                )
        );

        var user = userRepository.findByEmail(signIn.getEmail());
        String token = jwtService.generateToken(user.get());
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
