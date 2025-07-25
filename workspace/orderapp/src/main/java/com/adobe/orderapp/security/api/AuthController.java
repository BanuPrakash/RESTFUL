package com.adobe.orderapp.security.api;

import com.adobe.orderapp.security.dto.JwtTokenResponse;
import com.adobe.orderapp.security.dto.SignInRequest;
import com.adobe.orderapp.security.dto.SignUpRequest;
import com.adobe.orderapp.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private  final AuthenticationService service;

    @PostMapping("/register")
    public JwtTokenResponse register(@RequestBody SignUpRequest request) {
        System.out.println("Entered!!!");
        return new JwtTokenResponse(service.signup(request));
    }

    @PostMapping("/login")
    public JwtTokenResponse login(@RequestBody SignInRequest request) {
        return new JwtTokenResponse(service.signIn(request));
    }
}
