package com.example.isyncpos.authentication;

public class ServerUserAuthentication {

    private int id;

    private String name, username, token;

    public ServerUserAuthentication(int id, String name, String username, String token) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.token = token;
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

    public String getToken() {
        return token;
    }

}
