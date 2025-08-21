package com.example.Lumiere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Lumiere.dto.ArticleDto;
import com.example.Lumiere.service.ArticleService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ArticleDto>> getActiveArticles() {
        List<ArticleDto> articles = articleService.getActiveArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/inventory/{inventoryId}")
    public ResponseEntity<List<ArticleDto>> getArticlesByInventory(@PathVariable Long inventoryId) {
        List<ArticleDto> articles = articleService.getArticlesByInventory(inventoryId);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        Optional<ArticleDto> article = articleService.getArticleById(id);
        return article.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ArticleDto> getArticleByCode(@PathVariable String code) {
        Optional<ArticleDto> article = articleService.getArticleByCode(code);
        return article.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        ArticleDto createdArticle = articleService.createArticle(articleDto);
        return ResponseEntity.ok(createdArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDto articleDto) {
        Optional<ArticleDto> updatedArticle = articleService.updateArticle(id, articleDto);
        return updatedArticle.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        boolean deleted = articleService.deleteArticle(id);
        if (deleted) {
            return ResponseEntity.ok().body("Article deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ArticleDto> toggleArticleStatus(@PathVariable Long id) {
        Optional<ArticleDto> updatedArticle = articleService.toggleArticleStatus(id);
        return updatedArticle.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
}