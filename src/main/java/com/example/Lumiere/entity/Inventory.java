package com.example.Lumiere.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "inventories")
@Data
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventaire")
    private Long idInventaire;
    
    @Column(name = "date_creation_inventaire", nullable = false)
    private LocalDateTime dateCreationInventaire;
    
    @Column(name = "nom_inventaire", nullable = false, length = 100)
    private String nomInventaire;
    
    @Column(name = "description_inventaire", columnDefinition = "TEXT")
    private String descriptionInventaire;
    
    @Column(name = "emplacement_inventaire", length = 100)
    private String emplacementInventaire;
    
    @Column(name = "responsable_inventaire", length = 100)
    private String responsableInventaire;
    
    @Column(name = "statut_inventaire", nullable = false)
    private boolean statutInventaire = true; // true = actif, false = inactif
    
    @PrePersist
    protected void onCreate() {
        dateCreationInventaire = LocalDateTime.now();
    }
    
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();
    
}