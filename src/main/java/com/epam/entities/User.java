package com.epam.entities;

import jakarta.persistence.*;
import lombok.*;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="user")

public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int userId;

    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private boolean disabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;
}
