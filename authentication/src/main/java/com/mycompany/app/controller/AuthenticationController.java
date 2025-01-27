package com.mycompany.app.controller;


import com.mycompany.app.record.AuthenticationRequest;
import com.mycompany.app.record.AuthenticationResponse;
import com.mycompany.app.record.UserRequest;
import com.mycompany.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(userService.authenticateUser(authenticationRequest));
    }
}
