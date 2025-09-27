
package com.registro.REGISTRO.service;

import com.registro.REGISTRO.model.Cliente;
import com.registro.REGISTRO.model.Compra;
import com.registro.REGISTRO.model.CompraDetallePedido;
import com.registro.REGISTRO.repository.CompraDetallePedidoRepository;
import com.registro.REGISTRO.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;
    
    @Autowired
    private CompraDetallePedidoRepository compradetallepedidoRepository;
    
        
    
    // busca una compra asociada a un cliente cuyo campo fechaCompra esté vacío
    public Compra obtenerCompraActiva(Cliente cliente) {
        return compraRepository.findByClienteAndFechaCompraIsNull(cliente);
    }
   
    
    public void finalizarCompra(Compra compra) {
        // Primero, guardamos la compra en la base de datos para que tenga un id_compra asignado.
        compraRepository.save(compra);

        // Luego, guardamos cada detalle de la compra.
        for (CompraDetallePedido detalle : compra.getDetalles()) {// al hacer compra.getDetalles esta capturando todo un array donde se repite el id_compra
            
            //Al llamar a compra.getDetalles(), se obtiene una lista de todos los CompraDetalle que están actualmente en la colección detalles de esa Compra. Estos detalles están relacionados con la compra, pero aún no se han guardado en la base de datos.
            detalle.setCompra(compra);//Estoy es colocado de mi entidad CompraDetalle

            /*Cuando se guarda cada CompraDetalle en la base de datos, además de almacenar 
            el id_compra como clave foránea, también se guardan sus otros atributos
            (como cantidad, precioUnitario, y subtotal automaticamente , porque su atributo coinciden) tal y como estaban en 
            el objeto detalle*/
            
                        
            compradetallepedidoRepository.save(detalle);
        }
    }

    
    public Compra obtenerUltimaCompra(Cliente cliente) {
        // Aquí usas tu repositorio para encontrar la compra más reciente del cliente
        return compraRepository.findTopByClienteOrderByFechaCompraDesc(cliente);
    }

    
    
}
