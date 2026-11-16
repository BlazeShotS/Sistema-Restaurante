package com.registro.REGISTRO.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.registro.REGISTRO.model.Categoria;
import com.registro.REGISTRO.repository.CategoriaRepository;

@Service 
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

}
