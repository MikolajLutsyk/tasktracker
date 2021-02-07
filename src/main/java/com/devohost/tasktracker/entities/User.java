package com.devohost.tasktracker.entities;


import com.devohost.tasktracker.entities.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate registrationDate;
    @OneToOne
    private Tracker tracker;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
