package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Lumiere.dto.EntityDtoMapper;
import com.example.Lumiere.dto.InventoryDto;
import com.example.Lumiere.entity.Inventory;
import com.example.Lumiere.entity.User;
import com.example.Lumiere.repository.InventoryRepository;
import com.example.Lumiere.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityDtoMapper mapper;

    public List<InventoryDto> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(mapper::toInventoryDto)
                .collect(Collectors.toList());
    }

    public List<InventoryDto> getActiveInventories() {
        return inventoryRepository.findByStatutInventaire(true).stream()
                .map(mapper::toInventoryDto)
                .collect(Collectors.toList());
    }

    public Optional<InventoryDto> getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .map(mapper::toInventoryDto);
    }

    public InventoryDto createInventory(InventoryDto inventoryDto) {
        Inventory inventory = mapper.toInventoryEntity(inventoryDto);
        
        // Associer l'utilisateur si userId est fourni
        if (inventoryDto.getUserId() != null) {
            User user = userRepository.findById(inventoryDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            inventory.setUser(user);
        }
        
        Inventory savedInventory = inventoryRepository.save(inventory);
        return mapper.toInventoryDto(savedInventory);
    }

    public Optional<InventoryDto> updateInventory(Long id, InventoryDto inventoryDto) {
        return inventoryRepository.findById(id)
                .map(existingInventory -> {
                    existingInventory.setNomInventaire(inventoryDto.getNomInventaire());
                    existingInventory.setDescriptionInventaire(inventoryDto.getDescriptionInventaire());
                    existingInventory.setEmplacementInventaire(inventoryDto.getEmplacementInventaire());
                    existingInventory.setResponsableInventaire(inventoryDto.getResponsableInventaire());
                    existingInventory.setStatutInventaire(inventoryDto.isStatutInventaire());
                    
                    // Mettre à jour l'utilisateur si userId est fourni
                    if (inventoryDto.getUserId() != null) {
                        User user = userRepository.findById(inventoryDto.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        existingInventory.setUser(user);
                    }
                    
                    Inventory updatedInventory = inventoryRepository.save(existingInventory);
                    return mapper.toInventoryDto(updatedInventory);
                });
    }

    public boolean deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<InventoryDto> toggleInventoryStatus(Long id) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventory.setStatutInventaire(!inventory.isStatutInventaire());
                    Inventory updatedInventory = inventoryRepository.save(inventory);
                    return mapper.toInventoryDto(updatedInventory);
                });
    }
}