
package com.registro.REGISTRO.service;

import com.registro.REGISTRO.model.Cliente;
import com.registro.REGISTRO.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {
    
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente>listarTodas(){
        return clienteRepository.findAll();//recupera todo los registros de la base de datos es como un get
    }
    
     public Cliente guardar (Cliente cliente){
        return clienteRepository.save(cliente); //guarda los registros en la base de datos
    }
    
    public Cliente obtenerPorId (Long id_cliente){
        return clienteRepository.findById(id_cliente).orElse(null);
    }
    
    public void eliminar (Long id_cliente){
        clienteRepository.deleteById(id_cliente);
    }
    
    
    
    /*-----PÁRA EL INICIO DE SESION , CONFIRMAR SI LAS CREDENCIALES SON CORRECTAS------*/
    public Cliente obtenerPorIdLogin(String id) {
        Long idCliente = Long.parseLong(id);  // Convertir String a Long
        return clienteRepository.findByIdCliente(idCliente);
    }
    
}
