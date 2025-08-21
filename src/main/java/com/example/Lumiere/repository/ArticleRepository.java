package com.example.Lumiere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Lumiere.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByStatutArticle(boolean statut);
    List<Article> findByInventoryIdInventaire(Long inventoryId);
    Optional<Article> findByCodeArticle(String codeArticle);
    
    long countByStatutArticle(boolean statut);
}