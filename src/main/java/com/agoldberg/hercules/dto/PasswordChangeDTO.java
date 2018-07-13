package com.agoldberg.hercules.dto;

import com.agoldberg.hercules.constraint.PasswordMatching;

@PasswordMatching
public class PasswordChangeDTO implements MatchingPassword{
    private String currentPassword;
    private String password;
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
