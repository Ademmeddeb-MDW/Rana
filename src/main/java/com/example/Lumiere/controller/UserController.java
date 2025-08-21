package com.example.Lumiere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Lumiere.dto.UserDto;
import com.example.Lumiere.dto.UserResponseDto;
import com.example.Lumiere.dto.UserUpdateDto;
import com.example.Lumiere.service.UserService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        Optional<UserResponseDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserResponseDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        Optional<UserResponseDto> updatedUser = userService.updateUser(id, userUpdateDto);
        return updatedUser.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok().body("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<UserResponseDto> toggleUserStatus(@PathVariable Long id) {
        Optional<UserResponseDto> updatedUser = userService.toggleUserStatus(id);
        return updatedUser.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
}