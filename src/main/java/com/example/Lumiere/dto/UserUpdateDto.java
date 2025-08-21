package com.example.Lumiere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUpdateDto {
    private Long idUser;
    
    @Size(min = 3, max = 50, message = "Le login doit contenir entre 3 et 50 caractères")
    private String loginUser;
    
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nomUser;
    
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenomUser;
    
    @Size(max = 20, message = "Le mobile ne peut pas dépasser 20 caractères")
    private String mobileUser;
    
    @Size(max = 100, message = "Le site ne peut pas dépasser 100 caractères")
    private String siteUser;
    
    @Email(message = "L'email doit être valide")
    private String mail;
    
    private String pwdMail;
    private LocalDateTime dateCreationUser;
    private boolean active = true;
}