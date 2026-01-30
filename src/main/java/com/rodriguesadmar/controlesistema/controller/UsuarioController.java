package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.model.Usuario;
import com.rodriguesadmar.controlesistema.service.UsuarioService;
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

    @GetMapping("/novo")
    public String formularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/cadastro";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(Usuario usuario) {
        usuarioService.salvarUsuario(usuario);
        return "redirect:/usuarios/novo?sucesso";
    }

    @GetMapping
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios/lista";
    }
}
