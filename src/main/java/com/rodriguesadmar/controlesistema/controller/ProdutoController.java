package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.service.ProdutoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);
    

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @GetMapping("/")
    public String redirecionarRaiz() {
        return "redirect:/produtos";
    }

    @GetMapping
    public String listarProdutos(Model model){
        List<Produto> Produtos = produtoService.findAll();



        logger.info("total de produtos encontrados para a lista: {}", Produtos.size());
        if (!Produtos.isEmpty()){
            Produtos.forEach(p -> logger.info("Produto na lista - ID, nome {}",
                    p.getId(), p.getNome()));
        }else{
            logger.info("A lista está vazia.");
        }





        model.addAttribute("Produtos", Produtos);
        return "Produtos/lista";
    }


    @GetMapping("/novo")
    public String novoProdutoForm(Model model){
        model.addAttribute("produto", new Produto());
        return "produtos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute Produto produto, BindingResult result, Model model){

        if (result.hasErrors()) {
            logger.warn("Erros de validação encontrados ao salvar produto {}", result.getAllErrors());
            model.addAttribute("produto", produto);
            return "produtos/formulario";
        }

        produtoService.save(produto);
        logger.info("Produto salvo com sucesso: ID {}, nome {}", produto.getId(), produto.getNome());
        return "redirect:produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarProdutoForm(@PathVariable Long id, Model model){
        produtoService.findById(id).ifPresent(produto -> {
            model.addAttribute("produto", produto);
        });

        return "produtos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id){
        produtoService.deleteById(id);
        return "redirect:/produtos";
    }







}






















