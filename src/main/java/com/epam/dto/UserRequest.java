package com.epam.dto;

import com.epam.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequest {
    private int userId;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Username cannot be null")
    private String username;
    @Email(message = "Please provide proper email")
    private String email;
    @NotBlank(message = "Password cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric allowed")
    private String password;
    @NotNull(message = "Please select either 'USER' or 'ADMIN'")
    private Role role;



}
