package com.oneworldaccuracy.onboardingservice.vo;

import com.oneworldaccuracy.onboardingservice.vo.enums.Roles;
import com.oneworldaccuracy.onboardingservice.vo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

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

    public UserDto(String title, String firstName, String lastName, String email, String mobile, Roles role, LocalDateTime dateRegistered, LocalDateTime dateVerified,
                   LocalDateTime dateDeactivated, boolean verified, Status status, boolean deleted) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.dateRegistered = dateRegistered;
        this.dateVerified = dateVerified;
        this.dateDeactivated = dateDeactivated;
        this.verified = verified;
        this.deleted = deleted;
        this.status = status;
    }
}
