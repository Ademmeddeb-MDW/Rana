package com.example.Lumiere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Lumiere.dto.InventoryDto;
import com.example.Lumiere.service.InventoryService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventories() {
        List<InventoryDto> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/active")
    public ResponseEntity<List<InventoryDto>> getActiveInventories() {
        List<InventoryDto> inventories = inventoryService.getActiveInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long id) {
        Optional<InventoryDto> inventory = inventoryService.getInventoryById(id);
        return inventory.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@Valid @RequestBody InventoryDto inventoryDto) {
        InventoryDto createdInventory = inventoryService.createInventory(inventoryDto);
        return ResponseEntity.ok(createdInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventory(@PathVariable Long id, @Valid @RequestBody InventoryDto inventoryDto) {
        Optional<InventoryDto> updatedInventory = inventoryService.updateInventory(id, inventoryDto);
        return updatedInventory.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        boolean deleted = inventoryService.deleteInventory(id);
        if (deleted) {
            return ResponseEntity.ok().body("Inventory deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<InventoryDto> toggleInventoryStatus(@PathVariable Long id) {
        Optional<InventoryDto> updatedInventory = inventoryService.toggleInventoryStatus(id);
        return updatedInventory.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }
}