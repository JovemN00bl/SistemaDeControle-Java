package com.rodriguesadmar.controlesistema.service;

import com.rodriguesadmar.controlesistema.model.Usuario;
import com.rodriguesadmar.controlesistema.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public Usuario salvarUsuario(Usuario usuario) {

        return usuarioRepository.save(usuario);

    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
