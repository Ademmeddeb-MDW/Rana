package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Lumiere.dto.EntityDtoMapper;
import com.example.Lumiere.dto.UserDto;
import com.example.Lumiere.dto.UserResponseDto;
import com.example.Lumiere.dto.UserUpdateDto;
import com.example.Lumiere.entity.User;
import com.example.Lumiere.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityDtoMapper mapper;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toUserResponseDto);
    }

    public UserResponseDto createUser(UserDto userDto) {
        User user = mapper.toUserEntity(userDto);
        User savedUser = userRepository.save(user);
        return mapper.toUserResponseDto(savedUser);
    }

    public Optional<UserResponseDto> updateUser(Long id, UserUpdateDto userUpdateDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (userUpdateDto.getLoginUser() != null) {
                        existingUser.setLoginUser(userUpdateDto.getLoginUser());
                    }
                    if (userUpdateDto.getNomUser() != null) {
                        existingUser.setNomUser(userUpdateDto.getNomUser());
                    }
                    if (userUpdateDto.getPrenomUser() != null) {
                        existingUser.setPrenomUser(userUpdateDto.getPrenomUser());
                    }
                    if (userUpdateDto.getMobileUser() != null) {
                        existingUser.setMobileUser(userUpdateDto.getMobileUser());
                    }
                    if (userUpdateDto.getSiteUser() != null) {
                        existingUser.setSiteUser(userUpdateDto.getSiteUser());
                    }
                    if (userUpdateDto.getMail() != null) {
                        existingUser.setMail(userUpdateDto.getMail());
                    }
                    if (userUpdateDto.getPwdMail() != null) {
                        existingUser.setPwdMail(userUpdateDto.getPwdMail());
                    }
                    existingUser.setActive(userUpdateDto.isActive());
                    
                    User updatedUser = userRepository.save(existingUser);
                    return mapper.toUserResponseDto(updatedUser);
                });
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UserResponseDto> toggleUserStatus(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setActive(!user.isActive());
                    User updatedUser = userRepository.save(user);
                    return mapper.toUserResponseDto(updatedUser);
                });
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