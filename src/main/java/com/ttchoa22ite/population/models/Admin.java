package com.ttchoa22ite.population.models;


public final class Admin {
    private final String username;
    private final String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "Username='" + username + '\'' +
                ", Password='" + password + '\'' +
                '}';
    }
}
