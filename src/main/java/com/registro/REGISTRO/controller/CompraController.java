
package com.registro.REGISTRO.controller;

import com.registro.REGISTRO.model.Cliente;
import com.registro.REGISTRO.model.Compra;
import com.registro.REGISTRO.model.CompraDetallePedido;
import com.registro.REGISTRO.model.Plato;
import com.registro.REGISTRO.service.ClienteService;
import com.registro.REGISTRO.service.CompraDetallePedidoService;
import com.registro.REGISTRO.service.CompraService;
import com.registro.REGISTRO.service.PlatoService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/compras")
public class CompraController {
    
    @Autowired
    private PlatoService platoService;
    @Autowired
    private CompraService compraService;    
    @Autowired
    private CompraDetallePedidoService compradetallepedidoService;    
    @Autowired
    private ClienteService clienteService;


    
    @GetMapping("/login") 
    public String mostrarLogin() {
        return "ClienteLogin"; 
    }
            
    

    @GetMapping("/mostrarCarrito")
    public String mostrarCarrito(HttpSession session, Model model) {
        Compra compra = (Compra) session.getAttribute("compraActiva");
        if (compra == null) {
            compra = new Compra(); // Asegura que compra no sea null
        }
        if (compra.getDetalles() == null) {
            compra.setDetalles(new ArrayList<>()); // Inicializa la lista si es null
        }
        model.addAttribute("compra", compra);  // Añadir la compra al modelo
        return "mostrarPedido";  // Vista del pedido
    }

    
    // Confirmación de la compra (RESUMEN DE LA COMPRA)
    @GetMapping("/confirmacion")
    public String confirmacion(HttpSession session, Model model) {
        // Verificar que el cliente esté logueado
        Long idCliente = (Long) session.getAttribute("id_cliente");
        if (idCliente == null) {
            return "redirect:/compras/login";  // Si no está logueado, redirigir al login
        }

        // Obtener el cliente desde el idCliente
        Cliente cliente = clienteService.obtenerPorId(idCliente);
        // Obtener la última compra del cliente desde la base de datos (puedes ordenarlas por fecha)
        Compra compra = compraService.obtenerUltimaCompra(cliente);  // Método que debes agregar

        if (compra == null || compra.getDetalles().isEmpty()) {
            model.addAttribute("mensaje", "No hay productos en la compra.");
            return "redirect:/compras/mostrarCarrito";  // Si no hay productos, redirigir al carrito
        }

        // Aquí puedes agregar detalles adicionales sobre la compra
        model.addAttribute("compra", compra);
        model.addAttribute("mensaje", "¡Compra realizada con éxito!");

        // Limpiar la compra activa en la sesión si aún no se hizo
        session.removeAttribute("id_cliente");
        session.removeAttribute("nombre_cliente");
        session.removeAttribute("compraActiva");

        return "Confirmacion";

    }
    
    
    

    /*Para guardar de manera temporal*/
    @PostMapping("/añadirAlCarrito")
    public String añadirAlCarrito(@RequestParam("idPlato") Long idPlato, HttpSession session, Model model) {

        Long idCliente = (Long) session.getAttribute("id_cliente");
        if (idCliente == null) {
            return "redirect:/compras/login";  // Si no está logueado, redirigir al login
        }
        
        // Obtener el cliente y el producto
        Cliente cliente = clienteService.obtenerPorId(idCliente);
        Plato plato = platoService.obtenerPorId(idPlato);
        
        // Verificar si hay stock disponible
        if (plato.getStock() <= 0) {
            model.addAttribute("error", "Lo sentimos, Stock Agotado");
            return /*"redirect:/CompraDetalle/*/"catalogo";  // Redirigir al catálogo si no hay stock
        }


        // Obtener la compra activa , la variable es compraActiva
        Compra compra = (Compra) session.getAttribute("compraActiva");
        if (compra == null) {
            // Crear una nueva compra si no existe una activa
            compra = new Compra();
            compra.setCliente(cliente);/*COLOCARA EL id_cliente (pero en si captura todo mi objeto de ese id_cliente que capturo , sino que como  esta relacionado hace que se capture solo el cliente_id)*/
            compra.setFechaCompra(new Date());
            compra.setTotal(0.0);
            session.setAttribute("compraActiva", compra); //guardo en session con la variable compraActiva los atributos de mi entidad Compra , que posteriormente sera llamado 
            //le pongo session para que se mantenga la compra activa mientras navega por la aplicacion
        }
    
        // Verificar si el producto ya está en los detalles de la compra
        Optional<CompraDetallePedido> detalleExistente = compra.getDetalles().stream()
                .filter(d -> d.getPlato().getId_plato().equals(idPlato))
                .findFirst();

        if (detalleExistente.isPresent()) {
            // Si el detalle ya existe, aumentar la cantidad del producto
            CompraDetallePedido detalle = detalleExistente.get();
            detalle.setCantidad(detalle.getCantidad() + 1);
            detalle.calcularSubtotal();
        } else {
            //Una vez que se ha guardado la compra activa en la sesión, se mantiene durante toda la sesión, es decir, hasta que el cliente cierre la sesión o la sesión expire
            // Crear un detalle de compra
            CompraDetallePedido nuevoDetalle = new CompraDetallePedido();
            nuevoDetalle.setCompra(compra);/*COLOCARA EL id_compra (captura todo el objeto pero por su relacion , solo en esta columna llenara el id_compra)*/
            nuevoDetalle.setPlato(plato);/*COLOCARA EL id_producto (captura todo el objeto y tengo todos sus atributos pero por su relacion solo se llenara en esta columna el id_producto)*/
            nuevoDetalle.setCantidad(1);//una unidad por defecto
            nuevoDetalle.setPrecioUnitario(plato.getPrecio());
            nuevoDetalle.calcularSubtotal();// Calculamos el subtotal del detalle (Este metodo esta en CompraDetalle)

            // Añadir el nuevo detalle a la compra
            // Añadir el detalle al carrito (compra activa)
            //detalle ahora "pertenece" a esa compra, y se almacena en la lista de detalles (compra.getDetalles()).
            compra.agregarDetalle(nuevoDetalle);
        }

        // Actualizar el total de la compra sumando los subtotales de todos los detalles
        compra.setTotal(compra.getDetalles().stream().mapToDouble(CompraDetallePedido::getSubtotal).sum());

        // Redirigir al carrito de compras
        return "redirect:/compras/mostrarCarrito";
    }

    
    
    // Para guardar todo lo de la tabla temporal recién en la base de datos (CUANDO LE DOY AL BOTON COMPRAR)
    @PostMapping("/comprar")
    public String comprar(HttpSession session,Model model) {
        
        Long idCliente = (Long) session.getAttribute("id_cliente");
        if (idCliente == null) {
            return "redirect:/compras/login";
        }

        // Obtener la compra activa de la sesión
        Compra compra = (Compra) session.getAttribute("compraActiva");
        if (compra == null || compra.getDetalles().isEmpty()) {
            return "redirect:/compras/mostrarCarrito";  // Si no hay platos, redirigir al carrito
        }

        // Guardar la compra en la base de datos
        compraService.finalizarCompra(compra);

        // Descontar el stock de los productos comprados
        for (CompraDetallePedido detalle : compra.getDetalles()) {
            Plato plato = detalle.getPlato();
            int cantidadComprada = detalle.getCantidad();//cantidad de stock que el cliente selecciono para comprar

            // Descontar el stock del producto
            plato.descontarStock(cantidadComprada);
            platoService.actualizarProducto(plato);
        }

        model.addAttribute("compra", compra);
        
        // Limpiar la compra activa en la sesión
        session.removeAttribute("compraActiva");

        return "redirect:/compras/confirmacion";
    }
    
    
    
    /*-------PARA ELIMINAR UN idCompraDetalle de la tabla temporal*/
    @GetMapping("/eliminarDelPedido/{idPlato}")/*OJO la variable idProducto con la vista no es necesario que sean el mismo nombre */
    public String eliminarDelCarrito(@PathVariable("idPlato") Long idPlato, HttpSession session) {
        // Obtener la compra activa de la sesión
        Compra compra = (Compra) session.getAttribute("compraActiva");

        if (compra == null) {
        return "redirect:/compras/mostrarCarrito";  // Si no existe la compra activa, redirigir al carrito
    }
        
        if (compra != null) {
            // Buscar el detalle por idProducto y eliminarlo
            
            compra.getDetalles().removeIf(detalle -> detalle.getPlato().getId_plato().equals(idPlato));
            //ESTO LO QUE HACE ES BUSCAR DEL ID_COMPRA EN COMPRADETALLE TODO EL ID_COMPRA QUE SE REPITE CIERTO
            
            
            // Recalcular el total de la compra
            double nuevoTotal = compra.getDetalles().stream().mapToDouble(CompraDetallePedido::getSubtotal).sum();
            compra.setTotal(nuevoTotal);
        }

        // Redirigir de nuevo al carrito para ver los cambios
        return "redirect:/compras/mostrarCarrito";
    }

    
}
