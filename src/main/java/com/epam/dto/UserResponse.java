package com.epam.dto;

import com.epam.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserResponse {

    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Role role;
    private boolean disabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private String developerMessage;
}
