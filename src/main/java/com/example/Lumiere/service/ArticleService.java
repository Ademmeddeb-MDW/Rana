package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Lumiere.dto.ArticleDto;
import com.example.Lumiere.dto.EntityDtoMapper;
import com.example.Lumiere.entity.Article;
import com.example.Lumiere.entity.DeliveryPerson;
import com.example.Lumiere.entity.Inventory;
import com.example.Lumiere.repository.ArticleRepository;
import com.example.Lumiere.repository.DeliveryPersonRepository;
import com.example.Lumiere.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private EntityDtoMapper mapper;

    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(mapper::toArticleDto)
                .collect(Collectors.toList());
    }

    public List<ArticleDto> getActiveArticles() {
        return articleRepository.findByStatutArticle(true).stream()
                .map(mapper::toArticleDto)
                .collect(Collectors.toList());
    }

    public List<ArticleDto> getArticlesByInventory(Long inventoryId) {
        return articleRepository.findByInventoryIdInventaire(inventoryId).stream()
                .map(mapper::toArticleDto)
                .collect(Collectors.toList());
    }

    public Optional<ArticleDto> getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(mapper::toArticleDto);
    }

    public Optional<ArticleDto> getArticleByCode(String code) {
        return articleRepository.findByCodeArticle(code)
                .map(mapper::toArticleDto);
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = mapper.toArticleEntity(articleDto);
        
        // Associer l'inventaire si inventoryId est fourni
        if (articleDto.getInventoryId() != null) {
            Inventory inventory = inventoryRepository.findById(articleDto.getInventoryId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));
            article.setInventory(inventory);
        }
        
        // Associer le livreur si deliveryPersonId est fourni
        if (articleDto.getDeliveryPersonId() != null) {
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(articleDto.getDeliveryPersonId())
                    .orElseThrow(() -> new RuntimeException("Delivery person not found"));
            article.setDeliveryPerson(deliveryPerson);
        }
        
        Article savedArticle = articleRepository.save(article);
        return mapper.toArticleDto(savedArticle);
    }

    public Optional<ArticleDto> updateArticle(Long id, ArticleDto articleDto) {
        return articleRepository.findById(id)
                .map(existingArticle -> {
                    existingArticle.setCodeArticle(articleDto.getCodeArticle());
                    existingArticle.setNomArticle(articleDto.getNomArticle());
                    existingArticle.setDescriptionArticle(articleDto.getDescriptionArticle());
                    existingArticle.setCategorieArticle(articleDto.getCategorieArticle());
                    existingArticle.setPrixArticle(articleDto.getPrixArticle());
                    existingArticle.setQuantiteArticle(articleDto.getQuantiteArticle());
                    existingArticle.setSeuilAlerteArticle(articleDto.getSeuilAlerteArticle());
                    existingArticle.setStatutArticle(articleDto.isStatutArticle());
                    
                    // Mettre à jour l'inventaire si inventoryId est fourni
                    if (articleDto.getInventoryId() != null) {
                        Inventory inventory = inventoryRepository.findById(articleDto.getInventoryId())
                                .orElseThrow(() -> new RuntimeException("Inventory not found"));
                        existingArticle.setInventory(inventory);
                    }
                    
                    // Mettre à jour le livreur si deliveryPersonId est fourni
                    if (articleDto.getDeliveryPersonId() != null) {
                        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(articleDto.getDeliveryPersonId())
                                .orElseThrow(() -> new RuntimeException("Delivery person not found"));
                        existingArticle.setDeliveryPerson(deliveryPerson);
                    }
                    
                    Article updatedArticle = articleRepository.save(existingArticle);
                    return mapper.toArticleDto(updatedArticle);
                });
    }

    public boolean deleteArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ArticleDto> toggleArticleStatus(Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setStatutArticle(!article.isStatutArticle());
                    Article updatedArticle = articleRepository.save(article);
                    return mapper.toArticleDto(updatedArticle);
                });
    }
}