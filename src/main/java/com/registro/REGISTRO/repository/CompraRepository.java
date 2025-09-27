
package com.registro.REGISTRO.repository;

import com.registro.REGISTRO.model.Cliente;
import com.registro.REGISTRO.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompraRepository extends JpaRepository <Compra,Long>  {
    
    
    // Método para obtener la compra activa de un cliente (sin fecha de compra)
    Compra findByClienteAndFechaCompraIsNull(Cliente cliente);
    
    // Método para obtener la última compra de un cliente POR FECHA
    Compra findTopByClienteOrderByFechaCompraDesc(Cliente cliente);
    
}
