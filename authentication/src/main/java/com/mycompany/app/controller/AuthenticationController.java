package com.mycompany.app.controller;


import com.mycompany.app.record.AuthenticationRequest;
import com.mycompany.app.record.AuthenticationResponse;
import com.mycompany.app.record.UserRequest;
import com.mycompany.app.record.UserResponse;
import com.mycompany.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable(name = "userId") Integer userId) {
        return userService.getUserById(userId);
    }
}
