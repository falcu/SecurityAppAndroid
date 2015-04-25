package com.example.guido.securityapp.validators;

/**
* Created by guido on 4/25/15.
*/
public class PasswordPair
{
    private String password;
    private String reentryPassword;

    public PasswordPair(String password, String reentryPassword) {
        this.password = password;
        this.reentryPassword = reentryPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getReentryPassword() {
        return reentryPassword;
    }
}
