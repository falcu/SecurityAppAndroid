package com.example.guido.securityapp.models;

/**
 * Created by guido on 2/5/15.
 */
public class Member {
    protected String name;
    protected String email;

    public Member(){}

    public Member(String name,String email)
    {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
