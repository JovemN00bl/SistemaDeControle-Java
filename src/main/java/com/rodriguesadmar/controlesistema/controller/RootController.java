package com.rodriguesadmar.controlesistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {


    @GetMapping("/")
    public String redirecionarParaProdutos(){
        return "redirect:/produtos";
    }
}
