package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.service.ProdutoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);


    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public String salvar(@ModelAttribute Produto produto, RedirectAttributes attributes, Model model) {
        try {
            produtoService.save(produto);

            attributes.addFlashAttribute("mensagemSucesso", "Produto"
                    + produto.getNome() + "Salvo com sucesso!");

            return "redirect:/produtos";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagemErro", "Erro ao salvar: Já existe um produto com o mesmo Nome ou Código de Barras.");

            model.addAttribute("produto", produto);

            return "produtos/formulario";
        }

    }




    @GetMapping
    public String listarProdutos(Model model) {
        List<Produto> Produtos = produtoService.findAll();


        logger.info("total de produtos encontrados para a lista: {}", Produtos.size());
        if (!Produtos.isEmpty()) {
            Produtos.forEach(p -> logger.info("Produto na lista - ID, nome {}",
                    p.getId(), p.getNome()));
        } else {
            logger.info("A lista está vazia.");
        }


        model.addAttribute("Produtos", Produtos);
        return "produtos/lista";
    }


    @GetMapping("/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute Produto produto, BindingResult result, RedirectAttributes attributes, Model model) {

        if (result.hasErrors()) {
            logger.warn("Erros de validação encontrados ao salvar produto {}", result.getAllErrors());
            model.addAttribute("produto", produto);
            return "produtos/formulario";
        }

        try {
            produtoService.save(produto);
            logger.info("Produto salvo com sucesso: ID {}, nome {}", produto.getId(), produto.getNome());
            attributes.addFlashAttribute("mensagemSucesso", "Produto '" + produto.getNome() + "' salvo com sucesso!");
            return "redirect:/produtos";
        } catch (DataIntegrityViolationException e) {
            logger.error("Erro de persistência (Unique Constraint Violation): {}", e.getMessage());
            model.addAttribute("mensagemErro", "Erro ao salvar: Já existe um produto com o mesmo Nome ou Código de Barras.");
            model.addAttribute("produto", produto);
            return "produtos/formulario";

        }
    }


    @GetMapping("/editar/{id}")
    public String editarProdutoForm(@PathVariable Long id, Model model){
        produtoService.findById(id).ifPresent(produto -> {
            model.addAttribute("produto", produto);
        });

        return "produtos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, RedirectAttributes attributes){
        produtoService.findById(id).ifPresent(p -> {
            attributes.addFlashAttribute("mensagemSucesso", "produto " + p.getNome() + "excluído com sucesso!");
        });
        
        return "redirect:/produtos";
    }







}






















