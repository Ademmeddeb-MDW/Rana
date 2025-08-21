package com.example.Lumiere.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long idUser;
    private String loginUser;
    private String nomUser;
    private String prenomUser;
    private String mobileUser;
    private String siteUser;
    private String mail;
    private LocalDateTime dateCreationUser;
    private boolean active;
}