package com.devohost.tasktracker.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "User")
@Data
@AllArgsConstructor
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
    @OneToOne(fetch = FetchType.EAGER)
    private Tracker tracker;
    @OneToOne
    private Role role;

    public User() {
        Tracker tracker = new Tracker();
    }
}
