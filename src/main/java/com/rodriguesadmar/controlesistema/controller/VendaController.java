package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.service.ClienteService;
import com.rodriguesadmar.controlesistema.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final ClienteService clienteService;
    private final ProdutoService produtoService;


    @GetMapping("/nova")
    public String novaVenda(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("produtos", produtoService.findAll());
        return "vendas/formulario.html";
    }
}
