
package com.registro.REGISTRO.repository;

import com.registro.REGISTRO.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository <Cliente,Long>{
    
    Cliente findByIdCliente(Long idCliente);
}
