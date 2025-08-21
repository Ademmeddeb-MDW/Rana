package com.example.Lumiere.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String email;
    private String nom;
    private String prenom;
    private String role;
    
    public LoginResponse(String token, String email, String nom, String prenom, String role) {
        this.token = token;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }
}