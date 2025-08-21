package com.example.Lumiere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Lumiere.dto.DeliveryPersonDto;
import com.example.Lumiere.service.DeliveryPersonService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/delivery-persons")
public class DeliveryPersonController {

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @GetMapping
    public ResponseEntity<List<DeliveryPersonDto>> getAllDeliveryPersons() {
        List<DeliveryPersonDto> deliveryPersons = deliveryPersonService.getAllDeliveryPersons();
        return ResponseEntity.ok(deliveryPersons);
    }

    @GetMapping("/active")
    public ResponseEntity<List<DeliveryPersonDto>> getActiveDeliveryPersons() {
        List<DeliveryPersonDto> deliveryPersons = deliveryPersonService.getActiveDeliveryPersons();
        return ResponseEntity.ok(deliveryPersons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPersonDto> getDeliveryPersonById(@PathVariable Long id) {
        Optional<DeliveryPersonDto> deliveryPerson = deliveryPersonService.getDeliveryPersonById(id);
        return deliveryPerson.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DeliveryPersonDto> createDeliveryPerson(@Valid @RequestBody DeliveryPersonDto deliveryPersonDto) {
        DeliveryPersonDto createdDeliveryPerson = deliveryPersonService.createDeliveryPerson(deliveryPersonDto);
        return ResponseEntity.ok(createdDeliveryPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPersonDto> updateDeliveryPerson(@PathVariable Long id, @Valid @RequestBody DeliveryPersonDto deliveryPersonDto) {
        Optional<DeliveryPersonDto> updatedDeliveryPerson = deliveryPersonService.updateDeliveryPerson(id, deliveryPersonDto);
        return updatedDeliveryPerson.map(ResponseEntity::ok)
                                  .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryPerson(@PathVariable Long id) {
        boolean deleted = deliveryPersonService.deleteDeliveryPerson(id);
        if (deleted) {
            return ResponseEntity.ok().body("Delivery person deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<DeliveryPersonDto> toggleDeliveryPersonStatus(@PathVariable Long id) {
        Optional<DeliveryPersonDto> updatedDeliveryPerson = deliveryPersonService.toggleDeliveryPersonStatus(id);
        return updatedDeliveryPerson.map(ResponseEntity::ok)
                                  .orElse(ResponseEntity.notFound().build());
    }
}