package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.mapper.UsuarioMapper;
import com.rodriguesadmar.controlesistema.model.Usuario;
import com.rodriguesadmar.controlesistema.model.dto.UsuarioDTO;
import com.rodriguesadmar.controlesistema.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @GetMapping("/novo")
    public String formularioCadastro(Model model) {
        model.addAttribute("UsuarioDTO", new UsuarioDTO());
        return "usuarios/cadastro";
    }

//    @PostMapping("/salvar")
//    public String salvarUsuario(Usuario usuario) {
//        usuarioService.salvarUsuario(usuario);
//        return "redirect:/usuarios/novo?sucesso";
//    }

    @GetMapping
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios/lista";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@Valid UsuarioDTO userDto) {
        Usuario realUser = mapper.toEntity(userDto);
        usuarioService.salvarUsuario(realUser);
        return "redirect:/usuarios/novo?sucesso";

    }


}
