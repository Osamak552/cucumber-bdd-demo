package com.epam.services;

import com.epam.dao.UserRepository;
import com.epam.dto.UserRequest;
import com.epam.dto.UserResponse;
import com.epam.entities.User;
import com.epam.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user,UserResponse.class)).toList();
    }

    @Override
    public UserResponse getUserById(int userId) throws UserException {
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user,UserResponse.class))
                .orElseGet(() ->
                        UserResponse.builder().developerMessage("No user is present").build()
                );
    }

    @Override
    public UserResponse getUserByUsername(String username) throws UserException {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user,UserResponse.class))
                .orElseGet(() ->
                     UserResponse.builder().developerMessage("No user is present").build()
                );
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) throws UserException {
        UserResponse userResponse = null;
        try{
            User user = modelMapper.map(userRequest,User.class);
            user =  userRepository.save(user);
            userResponse = modelMapper.map(user,UserResponse.class);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new UserException("Admin unable to create user", ExceptionUtils.getStackTrace(e));
        }
        return userResponse;
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) throws UserException {
        UserResponse userResponse = null;
        try{
            getUserById(userRequest.getUserId());
            User user = modelMapper.map(userRequest,User.class);
            user =  userRepository.save(user);
            userResponse = modelMapper.map(user,UserResponse.class);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new UserException("Admin unable to update user", ExceptionUtils.getStackTrace(e));
        }
        return userResponse;
    }

    @Override
    public void deleteUser(int userId)  {
        userRepository.deleteById(userId);
    }
}
