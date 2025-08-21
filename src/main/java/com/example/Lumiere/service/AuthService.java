package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Lumiere.config.JwtUtils;
import com.example.Lumiere.dto.EntityDtoMapper;
import com.example.Lumiere.dto.LoginRequest;
import com.example.Lumiere.dto.LoginResponse;
import com.example.Lumiere.dto.UserDto;
import com.example.Lumiere.entity.User;
import com.example.Lumiere.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EntityDtoMapper mapper;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        return new LoginResponse(
            jwt,
            userDetails.getEmail(),
            userDetails.getUsername(),
            "", // Vous devrez peut-être adapter selon vos besoins
            userDetails.getAuthorities().iterator().next().getAuthority()
        );
    }

    public User registerUser(UserDto userDto) {
        if (userRepository.existsByMail(userDto.getMail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        if (userRepository.existsByLoginUser(userDto.getLoginUser())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        User user = mapper.toUserEntity(userDto);
        user.setPasswordUser(passwordEncoder.encode(userDto.getPasswordUser()));
        
        return userRepository.save(user);
    }
}