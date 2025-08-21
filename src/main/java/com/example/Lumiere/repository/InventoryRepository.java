package com.example.Lumiere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Lumiere.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByStatutInventaire(boolean statut);
    
    long countByStatutInventaire(boolean statut);
}