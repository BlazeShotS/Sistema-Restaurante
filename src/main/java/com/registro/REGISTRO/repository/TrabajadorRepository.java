/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro.REGISTRO.repository;

import com.registro.REGISTRO.model.Trabajador;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mikel
 */
public interface TrabajadorRepository  extends JpaRepository<Trabajador,Long>{
    
    Optional<Trabajador> findById(Long id_trabajador); //se encarga de buscar un administrador en la base de datos utilizando su ID, o un Optional.empty() si no se encuentra.
    
}
