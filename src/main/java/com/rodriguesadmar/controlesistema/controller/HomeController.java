package com.rodriguesadmar.controlesistema.controller;


import com.rodriguesadmar.controlesistema.repository.ClienteRepository;
import com.rodriguesadmar.controlesistema.repository.PedidoRepository;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    @GetMapping("/")
    public String home(Model model) {

        LocalDateTime inicioDia = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).with(LocalTime.MIN);

        Double vendasHoje = pedidoRepository.totalVendasApartirDe(inicioDia);
        Double vendasMes = pedidoRepository.totalVendasApartirDe(inicioMes);

        long totalProdutos = produtoRepository.count();
        long totalClientes = clienteRepository.count();
        var produtosBaixoEstoque = produtoRepository.findByQuantidadeEstoqueLessThan(5);


        model.addAttribute("vendasHoje", vendasHoje);
        model.addAttribute("vendasMes", vendasMes);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalProdutos", totalProdutos);
        model.addAttribute("alertasEstoque", produtosBaixoEstoque);

        return "home";


    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
