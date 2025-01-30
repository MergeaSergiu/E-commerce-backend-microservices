package com.mycompany.app.service.impl;

import com.mycompany.app.model.Role;
import com.mycompany.app.model.User;
import com.mycompany.app.record.AuthenticationRequest;
import com.mycompany.app.record.AuthenticationResponse;
import com.mycompany.app.record.UserRequest;
import com.mycompany.app.record.UserResponse;
import com.mycompany.app.repository.RoleRepository;
import com.mycompany.app.repository.UserRepository;
import com.mycompany.app.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtServiceImpl jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Override
    public void addUser(UserRequest userRequest) {

        Role userRole;

        userRole = roleRepository.findByName("USER").orElseThrow(() -> new EntityNotFoundException("Role USER was not found"));

        User foundUser = userRepository.findByEmail(userRequest.email()).orElse(null);
        if(foundUser != null) {
            throw new EntityExistsException("An account with this email already exists");
        }

        User user  = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .accountLocked(false)
                .enabled(true)
                .role(userRole).build();

        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.email(),
                        authenticationRequest.password())
        );
        User user = (User)authenticate.getPrincipal();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " was not found"));
        return UserResponse.builder()
                .userId(foundUser.getId())
                .username(foundUser.getEmail())
                .build();
    }
}
