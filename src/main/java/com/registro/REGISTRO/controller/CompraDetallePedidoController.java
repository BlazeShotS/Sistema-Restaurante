
package com.registro.REGISTRO.controller;

import com.registro.REGISTRO.service.PlatoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/pedido")
public class CompraDetallePedidoController {
    
    private final PlatoService platoService;

    CompraDetallePedidoController(PlatoService platoService) {
        this.platoService = platoService;
    }
    
    @GetMapping ("/platos")
    public String listarProductoPedido(Model model){
        model.addAttribute("platos",platoService.listarTodas());
        return "MostrarPlatos";
    }
    
}
