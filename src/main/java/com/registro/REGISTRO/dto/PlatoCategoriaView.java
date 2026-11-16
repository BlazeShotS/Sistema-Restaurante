package com.registro.REGISTRO.dto;

import com.registro.REGISTRO.flyweight.CategoriaFlyweight;
import com.registro.REGISTRO.model.Plato;

public class PlatoCategoriaView {

    private final Plato plato;
    private final CategoriaFlyweight categoria;

    public PlatoCategoriaView(Plato plato, CategoriaFlyweight categoria) {
        this.plato = plato;
        this.categoria = categoria;
    }

    public Plato getPlato() {
        return plato;
    }

    public CategoriaFlyweight getCategoria() {
        return categoria;
    }

}
