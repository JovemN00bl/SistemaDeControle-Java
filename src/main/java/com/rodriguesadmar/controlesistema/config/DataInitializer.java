package com.rodriguesadmar.controlesistema.config;

import com.rodriguesadmar.controlesistema.model.Usuario;
import com.rodriguesadmar.controlesistema.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername("admin");

        if (usuarioExistente.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            usuarioRepository.save(admin);
            System.out.println("---------------------------------");
            System.out.println(" USUÁRIO ADMIN CRIADO: admin / admin123 ");
            System.out.println("---------------------------------");
        }
    }
}

