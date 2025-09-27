/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.REGISTRO.controller;

import com.registro.REGISTRO.model.Trabajador;
import com.registro.REGISTRO.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author mikel
 */
@Controller
@RequestMapping("/trabajador")
public class TrabajadorController {    
    
    @Autowired
    private TrabajadorService trabajadorService;
    
    
    @GetMapping("/login") 
    public String mostrarLogin() {
        return "LoginTrabajador"; 
    }    
    
    @GetMapping("/panel") 
    public String dashboard() {
        return "PanelTrabajador"; 
    }
        
    
    
    @PostMapping("/login") // Manejador de la solicitud de inicio de sesión
    public String login(HttpSession session,@RequestParam("id") Long id, @RequestParam("contrasena") String contrasena,Model model ) {
        
        Trabajador trabajador = trabajadorService.validarAdministrador(id, contrasena);      
        
        if (trabajador != null) { // Verifica si se recibió un administrador válido
            
            // Guardar el administrador en la sesión
            session.setAttribute("id_trabajador", trabajador.getId_trabajador());
            return "redirect:/trabajador/panel"; 
        } else {
            // Si no es válido, agregar un mensaje de error
            model.addAttribute("error", "ID o contraseña incorrectos");
            return "LoginTrabajador"; 
        }
    }
    
    
    
    
}
