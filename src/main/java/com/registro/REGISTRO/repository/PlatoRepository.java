/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro.REGISTRO.repository;

import com.registro.REGISTRO.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mikel
 */

public interface PlatoRepository extends JpaRepository <Plato,Long> {
    
}
