package com.example.Lumiere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDto {
    private Long idInventaire;
    
    @NotBlank(message = "Le nom de l'inventaire est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nomInventaire;
    
    private String descriptionInventaire;
    
    @Size(max = 100, message = "L'emplacement ne peut pas dépasser 100 caractères")
    private String emplacementInventaire;
    
    @Size(max = 100, message = "Le responsable ne peut pas dépasser 100 caractères")
    private String responsableInventaire;
    
    private boolean statutInventaire = true;
    private LocalDateTime dateCreationInventaire;
    private Long userId; // Pour lier l'inventaire à un utilisateur
}