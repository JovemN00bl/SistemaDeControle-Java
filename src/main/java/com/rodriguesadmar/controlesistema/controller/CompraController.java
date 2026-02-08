package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.config.exception.ResourceNotFoundException;
import com.rodriguesadmar.controlesistema.model.PedidoCompra;
import com.rodriguesadmar.controlesistema.repository.FornecedorRepository;
import com.rodriguesadmar.controlesistema.repository.PedidoCompraRepository;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import com.rodriguesadmar.controlesistema.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoCompraRepository pedidoRepository;

    @GetMapping("/nova")
    public String novaCompra(Model model) {
        model.addAttribute("compra", new PedidoCompra());
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());

        return "compras/formulario";
    }

    @PostMapping("/salvar")
    public String salvarCompra(PedidoCompra compra) {
        try {
            compraService.salvar(compra);
            return "redirect:/compras";
        }catch (Exception e ) {
            e.printStackTrace();
            return "redirect:/compras/nova?erro";
        }
    }

    @GetMapping
    public String listaCompra(Model model) {
        model.addAttribute("compras", pedidoRepository.findAllByOrderByDataCompraDesc());
        return "compras/lista";
    }

    @GetMapping("/{id}")
    public String detalheCompras(@PathVariable Long id, Model model) {
        PedidoCompra pedido = pedidoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Compra Invalida " + id));
        model.addAttribute("pedido", pedido );
        return "compras/detalhes";

    }
}
