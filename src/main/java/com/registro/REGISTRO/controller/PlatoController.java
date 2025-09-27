/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.REGISTRO.controller;

import com.registro.REGISTRO.model.Plato;
import com.registro.REGISTRO.model.Trabajador;
import com.registro.REGISTRO.service.PlatoService;
import com.registro.REGISTRO.service.TrabajadorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 *
 * @author mikel
 */

@Controller
@RequestMapping("/plato")
public class PlatoController {   
    
    @GetMapping("/mostrarPlatos") 
    public String mostrarLogin() {
        return "MostrarPlatos"; 
    }
    
    private static String UPLOAD_DIR = "src/main/resources/static/img/"; // Directorio para guardar imágenes

    
    @Autowired
    private PlatoService platoService;        

    @Autowired
    private TrabajadorService trabajadorService;
    
    
    
    @GetMapping  // aca le digo que /productos es productos-list
    public String listarPlato(Model model){ //Carga todas las personas desde el servicio y las agrega al modelo para ser mostradas en la vista persona-list
        model.addAttribute("platos",platoService.listarTodas()); //el productoService se encarga del manejo y logica con la base de datos
        return "plato-list";
    }   
    
    
    @GetMapping("/nuevo") //RECUPERAR DATOS DEL SERVIDOR 
    public String mostrarFormularioNuevaPlato (Model model){
        model.addAttribute("plato",new Plato());//creamos una variable producto que es donde se almacenara cada persona que registremos
        return "plato-form";
    }
            
    
    
    
    @PostMapping("guardar") //envia datos al servidor   (recibe datos de un formulario)
    public String guardarPlato(@Valid @ModelAttribute Plato plato //GUARDANDO EN MI OBJETO Producto Como un set seria, colocando en mi objeto producto
                                  ,BindingResult result
                                  ,@RequestParam("foto") MultipartFile foto
                                  ,HttpSession session){//Creo un parametro producto este producto esta lleno porque antes con mi variable producto que esta arriba de verde ya lo llene, lo que hace este parametro es llevarlo a la db
        
        if (result.hasErrors()) { /*PARA LA VALIDACION*/
            return "plato-form";
        }
        
        
        // Obtener el ID del administrador desde la sesión (Controlador del administrador)
        Long idTrabajador = (Long) session.getAttribute("id_trabajador");
        
        // Buscar el administrador usando el idAdministrador recuperado
        Trabajador trabajador = trabajadorService.obtenerPorId(idTrabajador);
           
         if (trabajador == null) {
            throw new RuntimeException("Trabajador no encontrado");
        }  
         
        plato.setTrabajador(trabajador);
               
        if (!foto.isEmpty()) {
            try {
                // Creo la ruta donde se almacenará el archivo
                byte[] bytes = foto.getBytes();
                Path path = Paths.get(UPLOAD_DIR + foto.getOriginalFilename());
                Files.write(path, bytes);

                // Guardo la referencia de la imagen en el producto
                plato.setRutaImagen("/img/" + foto.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }                
                
        platoService.guardar(plato); //Guarda el objeto Producto en la base de datos 
        return "redirect:/plato";
    }
    
    

    /*Get Mapping es mas que todo para traer datos de la database*/
    @GetMapping("/editar/{id_plato}")//SE MOSTRARA EN LA URL DE MI NAVEGADOR , @PathVariable le dice a spring que tome el id de la URL
    public String mostrarFormularioEditarPlato (@PathVariable Long id_plato,Model model){
        model.addAttribute("plato", platoService.obtenerPorId(id_plato));
        return "plato-form";
    }
    
    @GetMapping("/eliminar/{id_plato}")
    public String eliminarPlato(@PathVariable Long id_plato){
        platoService.eliminar(id_plato);
        return "redirect:/plato";
    }
    
    
    
    
    
    
    
}
