package com.learnandearn.sundayfriends.network.model;

public class AuthHeader {
    private String idToken;
    private String idClient;
    private String idEmail;

    public AuthHeader(String idToken, String idClient, String idEmail) {
        this.idToken = idToken;
        this.idClient = idClient;
        this.idEmail = idEmail;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getIdClient() {
        return idClient;
    }

    public String getIdEmail() {
        return idEmail;
    }
}
