package com.registro.REGISTRO.flyweight;

public class CategoriaFlyweight {

    //Usamos final para que los datos compartidos no puedan modificarse después de crear el objeto
    private final Long id_categoria;
    private final String nombre;

    public CategoriaFlyweight(Long id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public String getNombre() {
        return nombre;
    }
}
