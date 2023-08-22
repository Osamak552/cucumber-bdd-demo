package com.epam.mockito.service;


import com.epam.dao.UserRepository;
import com.epam.dto.UserRequest;
import com.epam.dto.UserResponse;
import com.epam.entities.Role;
import com.epam.entities.User;
import com.epam.exception.UserException;
import com.epam.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void validCreateUser() throws UserException {
        UserRequest userRequest = new UserRequest(1, "Osama", "Khan", "username1", "osama@gmail.com", "password", Role.USER);
        User user = new User(1, "username1", "Osama", "Khan", "osama@gmail.com", "password", false, false, false, false, Role.USER);
        UserResponse expectedUserResponse = new UserResponse(1, "Osama", "Khan", "Osama@admin", "Osama@admin@gmail.com", "password", Role.USER, false, false, false, false, null);


        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedUserResponse);

        UserResponse actualUserResponse = userService.createUser(userRequest);

        assertEquals(expectedUserResponse, actualUserResponse);

        verify(modelMapper, times(1)).map(userRequest, User.class);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void invalidCreateUser()  {
        UserRequest userRequest = new UserRequest(1, "Osama", "Khan", "username1", "osama@gmail.com", "password", Role.USER);
        User user = new User(1, "username1", "Osama", "Khan", "osama@gmail.com", "password", false, false, false, false, Role.USER);
        UserResponse expectedUserResponse = new UserResponse(1, "Osama", "Khan", "Osama@admin", "Osama@admin@gmail.com", "password", Role.USER, false, false, false, false, null);


        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(UserException.class,() ->userService.createUser(userRequest));


        verify(modelMapper, times(1)).map(userRequest, User.class);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUsers() {
        User user1 = new User(1, "username1", "Osama", "Khan", "osama@gmail.com", "password", false, false, false, false, Role.USER);
        User user2 = new User(2, "username2", "Jane", "Khan", "janedoe@example.com", "password", false, false, false, false, Role.ADMIN);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(2, userService.getUsers().size());
        Mockito.verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() throws UserException {
        User user = new User(1, "Osama@admin", "Osama", "Khan", "Osama@admin@gmail.com", "password", false, false, false, false, Role.USER);
        UserResponse expectedUserResponse = new UserResponse(1, "Osama", "Khan", "Osama@admin", "Osama@admin@gmail.com", "password", Role.USER, false, false, false, false, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedUserResponse);
        assertEquals(1, userService.getUserById(1).getUserId());
        Mockito.verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testGetUserByIdNotFound() throws UserException {

        when(userRepository.findById(100)).thenReturn(Optional.ofNullable(null));
        assertEquals(0, userService.getUserById(100).getUserId());
        Mockito.verify(userRepository, times(1)).findById(100);
    }

    @Test
    public void testGetUserByUsername() throws UserException {
        User user = new User(1, "Osama@admin", "Osama", "Khan", "Osama@admin@gmail.com", "password", false, false, false, false, Role.USER);
        UserResponse expectedUserResponse = new UserResponse(1, "Osama", "Khan", "Osama@admin", "Osama@admin@gmail.com", "password", Role.USER, false, false, false, false, null);

        when(userRepository.findByUsername("Osama@admin")).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedUserResponse);

        UserResponse actualUserResponse = userService.getUserByUsername("Osama@admin");

        assertEquals(expectedUserResponse, actualUserResponse);
        Mockito.verify(userRepository).findByUsername("Osama@admin");
    }

    @Test
    public void testGetUserByUsernameNotFound() throws UserException {

        when(userRepository.findByUsername("Osama@admin")).thenReturn(Optional.ofNullable(null));
        UserResponse actualUserResponse = userService.getUserByUsername("Osama@admin");

        assertEquals("No user is present", actualUserResponse.getDeveloperMessage());
        Mockito.verify(userRepository).findByUsername("Osama@admin");
    }
    @Test
    void validUpdateUser() throws UserException {
        UserRequest userRequest = new UserRequest(1, "Osama", "Khan", "username1", "osama@gmail.com", "password", Role.USER);
        User user = new User(1, "username1", "Osama", "Khan", "osama@gmail.com", "password", false, false, false, false, Role.USER);
        UserResponse expectedUserResponse = new UserResponse(1, "Osama", "Khan", "Osama@admin", "Osama@admin@gmail.com", "password", Role.USER, false, false, false, false, null);

        when(userRepository.findById(userRequest.getUserId())).thenReturn(Optional.of(user));
        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedUserResponse);

        UserResponse actualUserResponse = userService.updateUser(userRequest);

        assertEquals(expectedUserResponse, actualUserResponse);
        verify(userRepository, times(1)).findById(userRequest.getUserId());
        verify(modelMapper, times(1)).map(userRequest, User.class);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void invalidUpdateUser()  {
        UserRequest userRequest = new UserRequest(1, "Osama", "Khan", "username1", "osama@gmail.com", "password", Role.USER);
        User user = new User(1, "username1", "Osama", "Khan", "osama@gmail.com", "password", false, false, false, false, Role.USER);
        UserResponse expectedUserResponse = new UserResponse(1, "Osama", "Khan", "Osama@admin", "Osama@admin@gmail.com", "password", Role.USER, false, false, false, false, null);

        when(userRepository.findById(userRequest.getUserId())).thenReturn(Optional.of(user));
        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedUserResponse);

        assertThrows(UserException.class,() -> userService.updateUser(userRequest));

        verify(userRepository, times(1)).findById(userRequest.getUserId());
        verify(modelMapper, times(1)).map(userRequest, User.class);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void validDeleteUser(){
        int userId = 1;
        Mockito.doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);
        verify(userRepository).deleteById(userId);
    }


}
