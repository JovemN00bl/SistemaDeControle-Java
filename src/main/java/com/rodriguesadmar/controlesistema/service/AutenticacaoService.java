package com.rodriguesadmar.controlesistema.service;

import com.rodriguesadmar.controlesistema.config.exception.ResourceNotFoundException;
import com.rodriguesadmar.controlesistema.model.Usuario;
import com.rodriguesadmar.controlesistema.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AutenticacaoService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;

    public UserDetails loadUserByUsername(String username) {
         Usuario name = usuarioRepository.findByUsername(username)
                 .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

//        String rolePrefixada = name.getRole().startsWith("ROLE_")
//                ? name.getRole()
//                : "ROLE_" + name.getRole();

         return User.builder()
                 .username(name.getUsername())
                 .password(name.getPassword())
                 .roles(name.getRole())
                 .build();
    }

}
