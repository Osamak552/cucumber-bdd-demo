package com.epam.services;

import com.epam.dto.UserRequest;
import com.epam.dto.UserResponse;
import com.epam.exception.UserException;

import java.util.*;

public interface UserService {
    public List<UserResponse> getUsers() ;
    public UserResponse getUserById(int userId) throws UserException;
    public UserResponse getUserByUsername(String username) throws UserException;
    public UserResponse createUser(UserRequest userRequest) throws UserException;
    public UserResponse updateUser(UserRequest userRequest) throws UserException;
    public void deleteUser(int userId) ;


}
