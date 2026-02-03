package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.model.Fornecedor;
import com.rodriguesadmar.controlesistema.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorRepository fornecedorRepository;

    @GetMapping
    public String listarFornecedores(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "fornecedores/lista";
    }

    @GetMapping("/novo")
    public String NovoFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/formulario";
    }

    @PostMapping("/salvar")
    public String salvarFornecedor(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        fornecedorRepository.findById(id).ifPresent(fornecedor ->
                model.addAttribute("fornecedor", fornecedor));

        return "fornecedores/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String deleteFornecedor(@PathVariable Long id, RedirectAttributes attributes) {
        try{
            fornecedorRepository.deleteById(id);
            attributes.addFlashAttribute("mensagemSucesso", "fornecedor excluido com sucesso !");

        }catch (Exception e) {
            attributes.addFlashAttribute("mensagemError", "Não foi possivel excluir o fornecedor!");
        }
        return "redirect:/fornecedores";
    }


}
