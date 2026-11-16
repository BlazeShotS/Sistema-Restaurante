package com.registro.REGISTRO.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.registro.REGISTRO.service.PlatoService;

@Controller 
public class InicioController {
    private final PlatoService platoService;

    public InicioController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("platos", platoService.listarTodas());
        return "MostrarPlatos";
    }

}