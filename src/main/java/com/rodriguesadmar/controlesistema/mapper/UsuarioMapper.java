package com.rodriguesadmar.controlesistema.mapper;


import com.rodriguesadmar.controlesistema.config.exception.ResourceNotFoundException;
import com.rodriguesadmar.controlesistema.model.Usuario;
import com.rodriguesadmar.controlesistema.model.dto.UsuarioDTO;
import com.rodriguesadmar.controlesistema.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.rodriguesadmar.controlesistema.config.SecurityConfig;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario toEntity(UsuarioDTO userDto) {

        Usuario usuario;
        if (userDto.getUsuarioId() != null) {
            usuario = userRepository.findById(userDto.getUsuarioId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Usuario não encontrado " + userDto.getUsuarioId()));
        }else {
            usuario = new Usuario();
        }

        usuario.setRole(userDto.getRole());
        usuario.setUsername(userDto.getUsername());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            String senhaCriptografada = passwordEncoder.encode(userDto.getPassword());
            usuario.setPassword(senhaCriptografada);
        }

        return usuario;


    }

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dtoUser = new UsuarioDTO();
        dtoUser.setUsuarioId(usuario.getId());
        dtoUser.setUsername(usuario.getUsername());
        dtoUser.setRole(usuario.getRole());
        return dtoUser;
    }




}
