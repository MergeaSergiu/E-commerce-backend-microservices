package com.mycompany.app.service;

import com.mycompany.app.record.AuthenticationRequest;
import com.mycompany.app.record.AuthenticationResponse;
import com.mycompany.app.record.UserRequest;

public interface UserService {

    void addUser(UserRequest userRequest);

    AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest);
}
