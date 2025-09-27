
package com.registro.REGISTRO.repository;

import com.registro.REGISTRO.model.CompraDetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompraDetallePedidoRepository extends JpaRepository <CompraDetallePedido, Long>{
    
        List<CompraDetallePedido> findByCompra_IdCompra(Long idCompra);//captura el fk id_compra

    
}
