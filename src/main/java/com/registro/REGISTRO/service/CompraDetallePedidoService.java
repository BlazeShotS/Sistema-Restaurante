
package com.registro.REGISTRO.service;

import com.registro.REGISTRO.model.CompraDetallePedido;
import com.registro.REGISTRO.repository.CompraDetallePedidoRepository;
import com.registro.REGISTRO.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompraDetallePedidoService {
    
    @Autowired
    private PlatoRepository platoRepository;
    
    @Autowired
    private CompraDetallePedidoRepository compradetallepedidoRepository;
    
    
    public void guardar(CompraDetallePedido detalle) {
        compradetallepedidoRepository.save(detalle);
    }    
    
    
}
