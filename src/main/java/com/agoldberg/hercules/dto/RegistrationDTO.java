package com.agoldberg.hercules.dto;

public class RegistrationDTO extends UserDTO{
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
