package com.example.Lumiere.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal; // Ajout de l'import pour BigDecimal


@Entity
@Table(name = "articles")
@Data
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private Long idArticle;
    
    @Column(name = "date_creation_article", nullable = false)
    private LocalDateTime dateCreationArticle;
    
    @Column(name = "code_article", nullable = false, unique = true, length = 50)
    private String codeArticle;
    
    @Column(name = "nom_article", nullable = false, length = 200)
    private String nomArticle;
    
    @Column(name = "description_article", columnDefinition = "TEXT")
    private String descriptionArticle;
    
    @Column(name = "categorie_article", length = 100)
    private String categorieArticle;
    
    // Changement de Double à BigDecimal avec precision et scale
    @Column(name = "prix_article", precision = 10, scale = 2)
    private BigDecimal prixArticle;
    
    @Column(name = "quantite_article")
    private Integer quantiteArticle;
    
    @Column(name = "seuil_alerte_article")
    private Integer seuilAlerteArticle;
    
    @Column(name = "statut_article", nullable = false)
    private boolean statutArticle = true;
    
    @ManyToOne
    @JoinColumn(name = "id_inventaire")
    private Inventory inventory;
    
    @ManyToOne
    @JoinColumn(name = "id_livreur")
    private DeliveryPerson deliveryPerson;
    
    @PrePersist
    protected void onCreate() {
        dateCreationArticle = LocalDateTime.now();
    }
}
