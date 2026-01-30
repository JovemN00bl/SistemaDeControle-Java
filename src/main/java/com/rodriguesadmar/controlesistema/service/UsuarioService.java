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
    private final PasswordEncoder passwordEncoder;


    public Usuario salvarUsuario(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        return usuarioRepository.save(usuario);

    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
