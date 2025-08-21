package com.example.Lumiere.entity;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    
    @Column(name = "date_creation_user", nullable = false)
    private LocalDateTime dateCreationUser;
    
    @Column(name = "login_user", nullable = false, unique = true, length = 50)
    private String loginUser;
    
    @Column(name = "mobile_user", length = 20)
    private String mobileUser;
    
    @Column(name = "nom_user", nullable = false, length = 100)
    private String nomUser;
    
    @Column(name = "password_user", nullable = false)
    private String passwordUser;
    
    @Column(name = "prenom_user", nullable = false, length = 100)
    private String prenomUser;
    
    @Column(name = "site_user", length = 100)
    private String siteUser;
    
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    
    @Column(name = "pwd_mail")
    private String pwdMail;
    
    @Column(name = "active", nullable = false)
    private boolean active = true; // Ajout du champ manquant
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Inventory> inventories = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        dateCreationUser = LocalDateTime.now();
    }
    
    public User() {
    }
    
    public User(String loginUser, String nomUser, String passwordUser, String prenomUser, String mail) {
        this.loginUser = loginUser;
        this.nomUser = nomUser;
        this.passwordUser = passwordUser;
        this.prenomUser = prenomUser;
        this.mail = mail;
        this.dateCreationUser = LocalDateTime.now();
        this.active = true; // Initialisation du nouveau champ
    }
}