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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "delivery_persons")
@Data
public class DeliveryPerson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livreur")
    private Long idLivreur;
    
    @Column(name = "date_creation_livreur", nullable = false)
    private LocalDateTime dateCreationLivreur;
    
    @Column(name = "nom_livreur", nullable = false, length = 100)
    private String nomLivreur;
    
    @Column(name = "prenom_livreur", nullable = false, length = 100)
    private String prenomLivreur;
    
    @Column(name = "mobile_livreur", length = 20)
    private String mobileLivreur;
    
    @Column(name = "vehicule_livreur", length = 50)
    private String vehiculeLivreur;
    
    @Column(name = "statut_livreur", nullable = false)
    private boolean statutLivreur = true; // true = actif, false = inactif
    
    @Column(name = "email_livreur")
    private String emailLivreur;
    
    @Column(name = "notes_livreur", columnDefinition = "TEXT")
    private String notesLivreur;
    
    @PrePersist
    protected void onCreate() {
        dateCreationLivreur = LocalDateTime.now();
    }
    
    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL)
    private List<Article> articlesLivres = new ArrayList<>();
    
}