package com.example.Lumiere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryPersonDto {
    private Long idLivreur;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nomLivreur;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenomLivreur;
    
    @Size(max = 20, message = "Le mobile ne peut pas dépasser 20 caractères")
    private String mobileLivreur;
    
    @Size(max = 50, message = "Le véhicule ne peut pas dépasser 50 caractères")
    private String vehiculeLivreur;
    
    @Email(message = "L'email doit être valide")
    private String emailLivreur;
    
    private String notesLivreur;
    private boolean statutLivreur = true;
    private LocalDateTime dateCreationLivreur;
}