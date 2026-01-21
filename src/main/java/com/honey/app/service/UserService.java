package com.honey.app.service;

import com.honey.app.io.UserRequest;
import com.honey.app.io.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest request);
    String findByUserId();
}
