package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Lumiere.dto.DeliveryPersonDto;
import com.example.Lumiere.dto.EntityDtoMapper;
import com.example.Lumiere.entity.DeliveryPerson;
import com.example.Lumiere.repository.DeliveryPersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private EntityDtoMapper mapper;

    public List<DeliveryPersonDto> getAllDeliveryPersons() {
        return deliveryPersonRepository.findAll().stream()
                .map(mapper::toDeliveryPersonDto)
                .collect(Collectors.toList());
    }

    public List<DeliveryPersonDto> getActiveDeliveryPersons() {
        return deliveryPersonRepository.findByStatutLivreur(true).stream()
                .map(mapper::toDeliveryPersonDto)
                .collect(Collectors.toList());
    }

    public Optional<DeliveryPersonDto> getDeliveryPersonById(Long id) {
        return deliveryPersonRepository.findById(id)
                .map(mapper::toDeliveryPersonDto);
    }

    public DeliveryPersonDto createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
        DeliveryPerson deliveryPerson = mapper.toDeliveryPersonEntity(deliveryPersonDto);
        DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        return mapper.toDeliveryPersonDto(savedDeliveryPerson);
    }

    public Optional<DeliveryPersonDto> updateDeliveryPerson(Long id, DeliveryPersonDto deliveryPersonDto) {
        return deliveryPersonRepository.findById(id)
                .map(existingDeliveryPerson -> {
                    existingDeliveryPerson.setNomLivreur(deliveryPersonDto.getNomLivreur());
                    existingDeliveryPerson.setPrenomLivreur(deliveryPersonDto.getPrenomLivreur());
                    existingDeliveryPerson.setMobileLivreur(deliveryPersonDto.getMobileLivreur());
                    existingDeliveryPerson.setVehiculeLivreur(deliveryPersonDto.getVehiculeLivreur());
                    existingDeliveryPerson.setEmailLivreur(deliveryPersonDto.getEmailLivreur());
                    existingDeliveryPerson.setNotesLivreur(deliveryPersonDto.getNotesLivreur());
                    existingDeliveryPerson.setStatutLivreur(deliveryPersonDto.isStatutLivreur());
                    
                    DeliveryPerson updatedDeliveryPerson = deliveryPersonRepository.save(existingDeliveryPerson);
                    return mapper.toDeliveryPersonDto(updatedDeliveryPerson);
                });
    }

    public boolean deleteDeliveryPerson(Long id) {
        if (deliveryPersonRepository.existsById(id)) {
            deliveryPersonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<DeliveryPersonDto> toggleDeliveryPersonStatus(Long id) {
        return deliveryPersonRepository.findById(id)
                .map(deliveryPerson -> {
                    deliveryPerson.setStatutLivreur(!deliveryPerson.isStatutLivreur());
                    DeliveryPerson updatedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
                    return mapper.toDeliveryPersonDto(updatedDeliveryPerson);
                });
    }
}