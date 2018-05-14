package com.agoldberg.hercules.dto;

import com.agoldberg.hercules.constraint.PasswordMatching;

import javax.validation.constraints.NotEmpty;

@PasswordMatching
public class RegistrationDTO extends UserDTO{
    @NotEmpty
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
