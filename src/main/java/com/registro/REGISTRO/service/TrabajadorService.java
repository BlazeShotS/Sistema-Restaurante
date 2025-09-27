/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.REGISTRO.service;

import com.registro.REGISTRO.model.Trabajador;
import com.registro.REGISTRO.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mikel
 */

@Service
public class TrabajadorService {
    
    @Autowired
    private TrabajadorRepository trabajadorRepository;
    
    
    public Trabajador validarAdministrador(Long id, String contrasena) {
        Trabajador trabajador = trabajadorRepository.findById(id).orElse(null);//captura toda esa fila de la tabla administrador y lo almaceno en el objeto Administrador
        if (trabajador != null && trabajador.getContrasena().equals(contrasena)) {
            return trabajador; // Devuelve el objeto lleno si las credenciales son correctas
        }
        return null; // Devuelve null si no es válido
    }
    
    
    public Trabajador obtenerPorId(Long id) {
        return trabajadorRepository.findById(id).orElse(null);
    }
    
    
    
}
