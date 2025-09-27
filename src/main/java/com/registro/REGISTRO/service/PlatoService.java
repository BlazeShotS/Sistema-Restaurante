/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.REGISTRO.service;

import com.registro.REGISTRO.model.Plato;
import com.registro.REGISTRO.repository.PlatoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mikel
 */

@Service
public class PlatoService {
    
    @Autowired
    private PlatoRepository platoRepository;

    
     public List<Plato>listarTodas(){
        return platoRepository.findAll();//recupera todo los registros de la base de datos es como un get
    }
    
    
    public Plato guardar (Plato plato){
        return platoRepository.save(plato); //guarda los registros en la base de datos
    }
    
    public Plato obtenerPorId (Long plato){
        return platoRepository.findById(plato).orElse(null);
    }
    
    public void eliminar (Long plato){
        platoRepository.deleteById(plato);
    }
    
    
    
    public void actualizarProducto(Plato plato) {
        platoRepository.save(plato);  // Guarda los cambios del producto en la base de datos
    }
    
    
    
    
}
