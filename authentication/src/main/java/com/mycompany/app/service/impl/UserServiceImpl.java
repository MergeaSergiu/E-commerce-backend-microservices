package com.mycompany.app.service.impl;

import com.mycompany.app.model.User;
import com.mycompany.app.record.UserRequest;
import com.mycompany.app.repository.UserRepository;
import com.mycompany.app.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void addUser(UserRequest userRequest) {

        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
        userRepository.save(user);

    }
}
