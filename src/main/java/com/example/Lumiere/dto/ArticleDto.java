package com.example.Lumiere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ArticleDto {
    private Long idArticle;
    
    @NotBlank(message = "Le code article est obligatoire")
    @Size(max = 50, message = "Le code ne peut pas dépasser 50 caractères")
    private String codeArticle;
    
    @NotBlank(message = "Le nom de l'article est obligatoire")
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String nomArticle;
    
    private String descriptionArticle;
    
    @Size(max = 100, message = "La catégorie ne peut pas dépasser 100 caractères")
    private String categorieArticle;
    
    private BigDecimal prixArticle;
    private Integer quantiteArticle;
    private Integer seuilAlerteArticle;
    private boolean statutArticle = true;
    private LocalDateTime dateCreationArticle;
    private Long inventoryId; // Pour lier l'article à un inventaire
    private Long deliveryPersonId; // Pour lier l'article à un livreur
}