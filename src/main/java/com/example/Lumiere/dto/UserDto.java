package com.example.Lumiere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long idUser;
    
    @NotBlank(message = "Le login est obligatoire")
    @Size(min = 3, max = 50, message = "Le login doit contenir entre 3 et 50 caractères")
    private String loginUser;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nomUser;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenomUser;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String passwordUser;
    
    @Size(max = 20, message = "Le mobile ne peut pas dépasser 20 caractères")
    private String mobileUser;
    
    @Size(max = 100, message = "Le site ne peut pas dépasser 100 caractères")
    private String siteUser;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String mail;
    
    private String pwdMail;
    private LocalDateTime dateCreationUser;
    private boolean active = true;
}