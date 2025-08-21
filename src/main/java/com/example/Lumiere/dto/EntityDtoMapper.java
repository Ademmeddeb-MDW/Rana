package com.example.Lumiere.dto;

import com.example.Lumiere.dto.*;
import com.example.Lumiere.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {
    
    // User mappings
	public UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setIdUser(user.getIdUser());
        dto.setLoginUser(user.getLoginUser());
        dto.setNomUser(user.getNomUser());
        dto.setPrenomUser(user.getPrenomUser());
        dto.setMobileUser(user.getMobileUser());
        dto.setSiteUser(user.getSiteUser());
        dto.setMail(user.getMail());
        dto.setDateCreationUser(user.getDateCreationUser());
        dto.setActive(user.isActive()); // Utilisation du getter correct
        return dto;
    }
    
    public User toUserEntity(UserDto userDto) {
        User user = new User();
        user.setLoginUser(userDto.getLoginUser());
        user.setNomUser(userDto.getNomUser());
        user.setPrenomUser(userDto.getPrenomUser());
        user.setPasswordUser(userDto.getPasswordUser());
        user.setMobileUser(userDto.getMobileUser());
        user.setSiteUser(userDto.getSiteUser());
        user.setMail(userDto.getMail());
        user.setPwdMail(userDto.getPwdMail());
        user.setActive(userDto.isActive()); // Utilisation du setter correct
        return user;
    }
    
    // DeliveryPerson mappings
    public DeliveryPersonDto toDeliveryPersonDto(DeliveryPerson deliveryPerson) {
        DeliveryPersonDto dto = new DeliveryPersonDto();
        dto.setIdLivreur(deliveryPerson.getIdLivreur());
        dto.setNomLivreur(deliveryPerson.getNomLivreur());
        dto.setPrenomLivreur(deliveryPerson.getPrenomLivreur());
        dto.setMobileLivreur(deliveryPerson.getMobileLivreur());
        dto.setVehiculeLivreur(deliveryPerson.getVehiculeLivreur());
        dto.setEmailLivreur(deliveryPerson.getEmailLivreur());
        dto.setNotesLivreur(deliveryPerson.getNotesLivreur());
        dto.setStatutLivreur(deliveryPerson.isStatutLivreur());
        dto.setDateCreationLivreur(deliveryPerson.getDateCreationLivreur());
        return dto;
    }
    
    public DeliveryPerson toDeliveryPersonEntity(DeliveryPersonDto dto) {
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setNomLivreur(dto.getNomLivreur());
        deliveryPerson.setPrenomLivreur(dto.getPrenomLivreur());
        deliveryPerson.setMobileLivreur(dto.getMobileLivreur());
        deliveryPerson.setVehiculeLivreur(dto.getVehiculeLivreur());
        deliveryPerson.setEmailLivreur(dto.getEmailLivreur());
        deliveryPerson.setNotesLivreur(dto.getNotesLivreur());
        deliveryPerson.setStatutLivreur(dto.isStatutLivreur());
        return deliveryPerson;
    }
    
    // Inventory mappings
    public InventoryDto toInventoryDto(Inventory inventory) {
        InventoryDto dto = new InventoryDto();
        dto.setIdInventaire(inventory.getIdInventaire());
        dto.setNomInventaire(inventory.getNomInventaire());
        dto.setDescriptionInventaire(inventory.getDescriptionInventaire());
        dto.setEmplacementInventaire(inventory.getEmplacementInventaire());
        dto.setResponsableInventaire(inventory.getResponsableInventaire());
        dto.setStatutInventaire(inventory.isStatutInventaire());
        dto.setDateCreationInventaire(inventory.getDateCreationInventaire());
        if (inventory.getUser() != null) {
            dto.setUserId(inventory.getUser().getIdUser());
        }
        return dto;
    }
    
    public Inventory toInventoryEntity(InventoryDto dto) {
        Inventory inventory = new Inventory();
        inventory.setNomInventaire(dto.getNomInventaire());
        inventory.setDescriptionInventaire(dto.getDescriptionInventaire());
        inventory.setEmplacementInventaire(dto.getEmplacementInventaire());
        inventory.setResponsableInventaire(dto.getResponsableInventaire());
        inventory.setStatutInventaire(dto.isStatutInventaire());
        return inventory;
    }
    
    // Article mappings
    public ArticleDto toArticleDto(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.setIdArticle(article.getIdArticle());
        dto.setCodeArticle(article.getCodeArticle());
        dto.setNomArticle(article.getNomArticle());
        dto.setDescriptionArticle(article.getDescriptionArticle());
        dto.setCategorieArticle(article.getCategorieArticle());
        dto.setPrixArticle(article.getPrixArticle());
        dto.setQuantiteArticle(article.getQuantiteArticle());
        dto.setSeuilAlerteArticle(article.getSeuilAlerteArticle());
        dto.setStatutArticle(article.isStatutArticle());
        dto.setDateCreationArticle(article.getDateCreationArticle());
        if (article.getInventory() != null) {
            dto.setInventoryId(article.getInventory().getIdInventaire());
        }
        if (article.getDeliveryPerson() != null) {
            dto.setDeliveryPersonId(article.getDeliveryPerson().getIdLivreur());
        }
        return dto;
    }
    
    public Article toArticleEntity(ArticleDto dto) {
        Article article = new Article();
        article.setCodeArticle(dto.getCodeArticle());
        article.setNomArticle(dto.getNomArticle());
        article.setDescriptionArticle(dto.getDescriptionArticle());
        article.setCategorieArticle(dto.getCategorieArticle());
        article.setPrixArticle(dto.getPrixArticle());
        article.setQuantiteArticle(dto.getQuantiteArticle());
        article.setSeuilAlerteArticle(dto.getSeuilAlerteArticle());
        article.setStatutArticle(dto.isStatutArticle());
        return article;
    }
    
    public User toUserEntity(UserUpdateDto userUpdateDto) {
        User user = new User();
        user.setLoginUser(userUpdateDto.getLoginUser());
        user.setNomUser(userUpdateDto.getNomUser());
        user.setPrenomUser(userUpdateDto.getPrenomUser());
        user.setMobileUser(userUpdateDto.getMobileUser());
        user.setSiteUser(userUpdateDto.getSiteUser());
        user.setMail(userUpdateDto.getMail());
        user.setPwdMail(userUpdateDto.getPwdMail());
        user.setActive(userUpdateDto.isActive());
        return user;
    }
}