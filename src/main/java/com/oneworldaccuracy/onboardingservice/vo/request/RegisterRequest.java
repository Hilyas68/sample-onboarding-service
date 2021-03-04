package com.oneworldaccuracy.onboardingservice.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String mobile;
    @NotBlank
    private String password;
}
