

package com.registro.REGISTRO.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class CompraDetallePedido {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompraDetalle;
    
    @ManyToOne
    @JoinColumn(name = "id_compra")  // Relación con Compra
    private Compra compra;
    
    
    @ManyToOne
    @JoinColumn(name = "id_plato")  // Relación con Producto
    private Plato plato;

    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    
    public Long getIdCompraDetalle() {
        return idCompraDetalle;
    }

    public void setIdCompraDetalle(Long idCompraDetalle) {
        this.idCompraDetalle = idCompraDetalle;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
 
    
    
    
}
