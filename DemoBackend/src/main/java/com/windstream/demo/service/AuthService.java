package com.windstream.demo.service;

import java.util.HashMap;

import com.windstream.demo.beans.Users;
import com.windstream.demo.response.Response;

public interface AuthService {
    Response register(Users userToAdd);
    HashMap<String, String> login(String username, String password);
    HashMap<String, String> refresh(String oldToken);
}
