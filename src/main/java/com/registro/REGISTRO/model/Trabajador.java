/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.REGISTRO.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


/**
 *
 * @author mikel
 */

@Entity
public class Trabajador {
    
     @Id
    /*@Pattern(regexp = "\\d{8}", message = "Debe contener 8 dígitos")*/
    private Long id_trabajador;
    
    private String nombre;
    private String correo;
    private String contrasena;

    
    
    public Long getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Long id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    
    
    
    
    
}
