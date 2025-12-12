package com.rodriguesadmar.controlesistema.controller;


import com.rodriguesadmar.controlesistema.model.Cliente;
import com.rodriguesadmar.controlesistema.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }
        try{
            clienteService.save(cliente);
            attributes.addFlashAttribute("mensagemSucesso", "Cliente salvo com Sucesso!");
            return "redirect:/clientes";
        }catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            model.addAttribute("mensagemErro", e.getMessage());
            return "clientes/formulario";

        } catch (DataIntegrityViolationException e) {
            logger.error("Erro ao salvar cliente: {}", e.getMessage());
            model.addAttribute("mensagemErro", "Erro: CPF/CNPJ ou E-mail já cadastrados no sistema.");
             return "clientes/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable Long id, Model model) {
        clienteService.findById(id).ifPresent(cliente -> {model.addAttribute("cliente", cliente);});
        return "clientes/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id, RedirectAttributes attributes ) {
        try {
            clienteService.deleteById(id);
            attributes.addFlashAttribute("mensagemSucesso", "Cliente excluido com sucesso !");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemError", "Não foi possivel excluir o cliente!");
        }
        return "redirect:/clientes";

    }
}
