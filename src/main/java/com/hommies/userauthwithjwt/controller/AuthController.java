package com.hommies.userauthwithjwt.controller;


import com.hommies.userauthwithjwt.dto.AuthResponse;
import com.hommies.userauthwithjwt.dto.RegisterRequest;
import com.hommies.userauthwithjwt.dto.SignInRequest;
import com.hommies.userauthwithjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("signUp")
    public ResponseEntity<AuthResponse> signUp(@RequestBody RegisterRequest registerRequest){
       return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest request){
        return ResponseEntity.ok(userService.login(request));
    }


}
