package com.example.isyncpos.authentication;

import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.entity.Roles;

import java.util.List;

public class UserAuthentication {

    private int id;
    private String name, username;

    private String accessToken;

    private List<Roles> roles;

    public UserAuthentication(int id, String name, String username, String accessToken, List<Roles> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public List<Roles> getRoles() {
        return roles;
    }

}
