package com.rodriguesadmar.controlesistema.controller;


import com.rodriguesadmar.controlesistema.repository.ClienteRepository;
import com.rodriguesadmar.controlesistema.repository.PedidoRepository;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    @GetMapping("/")
    public String home(Model model) {
        BigDecimal faturamento  = pedidoRepository.totalVendido();
        long totalPedidos = pedidoRepository.count();
        long totalClientes = clienteRepository.count();
        long produtosBaixoEstoque = produtoRepository.countByQuantidadeEstoqueLessThan(10);

        if (faturamento == null) {
            faturamento = BigDecimal.ZERO;
        }

        model.addAttribute("faturamento", faturamento);
        model.addAttribute("totalPedidos", totalPedidos);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("BaixoEstoque", produtosBaixoEstoque);
        return "home";


    }


}
