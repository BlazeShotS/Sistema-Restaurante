package com.registro.REGISTRO.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.registro.REGISTRO.model.Categoria;

@Component
public class CategoriaFlyweightFactory {//Objeto que se encarga de crear y gestionar los objetos Flyweight de la clase CategoriaFlyweight

    private final Map<Long, CategoriaFlyweight> categorias = new ConcurrentHashMap<>();
    //Recibe el objeto Categoria y devuelve un objeto CategoriaFlyweight correspondiente.
    public CategoriaFlyweight obtener(Categoria categoria) {
       //Comprueba que el plato tenga una categoría asignada
        if (categoria == null) {
            return null;
        }
        
        //Busca si ya existe un objeto Flyweight para esa categoría.
        //Si existe, devuelve la misma instancia.
        //Si no existe, crea una nueva y la almacena.
        //Los siguientes platos que tengan esa categoría reutilizarán el objeto
        return categorias.computeIfAbsent(categoria.getId_categoria(),id -> new CategoriaFlyweight(id,categoria.getNombre()));
    }

    public int cantidadFlyweights() {
        return categorias.size();
    }
}
