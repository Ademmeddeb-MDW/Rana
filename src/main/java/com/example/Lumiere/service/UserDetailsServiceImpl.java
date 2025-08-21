package com.example.Lumiere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Lumiere.entity.User;
import com.example.Lumiere.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Essayez d'abord de trouver par email
        User user = userRepository.findByMail(username)
                .orElseGet(() -> {
                    // Si non trouvé par email, essayez par login
                    return userRepository.findByLoginUser(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with identifier: " + username));
                });

        return UserDetailsImpl.build(user);
    }
}