package com.mycompany.app.service;

import com.mycompany.app.record.AuthenticationRequest;
import com.mycompany.app.record.AuthenticationResponse;
import com.mycompany.app.record.UserRequest;
import com.mycompany.app.record.UserResponse;

public interface UserService {

    void addUser(UserRequest userRequest);

    AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest);

    UserResponse getUserById(Integer userId);
}
