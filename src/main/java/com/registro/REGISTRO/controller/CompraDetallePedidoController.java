
package com.registro.REGISTRO.controller;

import com.registro.REGISTRO.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/pedido")
public class CompraDetallePedidoController {
    
    @Autowired
    private PlatoService platoService;
    
    @GetMapping ("/platos") // aca le digo que /productos es productos-list
    public String listarProductoPedido(Model model){ //Carga todas las personas desde el servicio y las agrega al modelo para ser mostradas en la vista persona-list
        model.addAttribute("platos",platoService.listarTodas()); //el productoService se encarga del manejo y logica con la base de datos
        return "MostrarPlatos";
    }
    
}
