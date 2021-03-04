package com.oneworldaccuracy.onboardingservice.model;

import com.oneworldaccuracy.onboardingservice.vo.enums.Roles;
import com.oneworldaccuracy.onboardingservice.vo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "USER_SQU", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USER_SQU", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private Roles role;
    private LocalDateTime dateRegistered;
    private LocalDateTime dateVerified;
    private LocalDateTime dateDeactivated;
    private boolean verified;
    private boolean deleted;
    private Status status;
    private String verificationCode;


}
