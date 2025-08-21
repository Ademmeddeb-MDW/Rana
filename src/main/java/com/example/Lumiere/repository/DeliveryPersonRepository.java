package com.example.Lumiere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Lumiere.entity.DeliveryPerson;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    List<DeliveryPerson> findByStatutLivreur(boolean statut);
    
    long countByStatutLivreur(boolean statut);
}