package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Lumiere.dto.DashboardStatsDto;
import com.example.Lumiere.repository.ArticleRepository;
import com.example.Lumiere.repository.DeliveryPersonRepository;
import com.example.Lumiere.repository.InventoryRepository;
import com.example.Lumiere.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public DashboardStatsDto getDashboardStats() {
        DashboardStatsDto stats = new DashboardStatsDto();
        
        stats.setTotalUsers(userRepository.count());
        stats.setTotalDeliveryPersons(deliveryPersonRepository.count());
        stats.setTotalInventories(inventoryRepository.count());
        stats.setTotalArticles(articleRepository.count());
        
        stats.setActiveUsers(userRepository.countByActive(true));
        stats.setActiveDeliveryPersons(deliveryPersonRepository.countByStatutLivreur(true));
        stats.setActiveInventories(inventoryRepository.countByStatutInventaire(true));
        stats.setActiveArticles(articleRepository.countByStatutArticle(true));
        
        return stats;
    }
}