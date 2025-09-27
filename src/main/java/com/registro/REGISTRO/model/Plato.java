
package com.registro.REGISTRO.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;


@Entity
public class Plato {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plato;
    
    @NotBlank(message = "Ingrese un nombre")
    @NotNull
    private String nombre;
    
    @NotBlank(message = "Ingrese la descripcion")
    @NotNull
    private String descripcion;
        
    
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @NotNull
    private Double precio;
    
    @PositiveOrZero(message = "La cantidad no puede ser negativo")
    @NotNull
    private int stock;
    
    
    @Column (name="fecha_ingreso")
    @PastOrPresent(message= "La fecha no puede ser futura")
    @NotNull
    LocalDate fechaIngreso;
    
    
    @Column(name = "ruta_Imagen")
    private String rutaImagen;

    
    @ManyToOne // Indica que este producto está relacionado con un administrador
               //Columna de la tabla producto         , Columna de la tabla administrador
    @JoinColumn(name = "id_trabajador", referencedColumnName = "id_trabajador")
    private Trabajador trabajador; // Relación con la entidad Administrador

    
    
    public void descontarStock(int cantidad) {
        if (this.stock >= cantidad) {
            this.stock -= cantidad;
        } else {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }
    }
    
    
    public Long getId_plato() {
        return id_plato;
    }

    public void setId_plato(Long id_plato) {
        this.id_plato = id_plato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    
    
    
    
    
    
}
