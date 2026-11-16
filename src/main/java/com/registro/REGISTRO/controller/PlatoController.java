package com.registro.REGISTRO.controller;

import com.registro.REGISTRO.dto.PlatoCategoriaView;
import com.registro.REGISTRO.flyweight.CategoriaFlyweightFactory;
import com.registro.REGISTRO.model.Categoria;
import com.registro.REGISTRO.model.Plato;
import com.registro.REGISTRO.model.Trabajador;
import com.registro.REGISTRO.service.CategoriaService;
import com.registro.REGISTRO.service.PlatoService;
import com.registro.REGISTRO.service.TrabajadorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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

@Controller
@RequestMapping("/plato")
public class PlatoController {

    PlatoController(PlatoService platoService, TrabajadorService trabajadorService, CategoriaService categoriaService, CategoriaFlyweightFactory categoriaFlyweightFactory){
        this.platoService = platoService;
        this.trabajadorService = trabajadorService;
        this.categoriaService = categoriaService;
        this.categoriaFlyweightFactory = categoriaFlyweightFactory;
    }

    private final PlatoService platoService;        
    private final TrabajadorService trabajadorService;
    private final CategoriaService categoriaService;
    private final CategoriaFlyweightFactory categoriaFlyweightFactory;
    
    @GetMapping
    public String listarPlato(Model model){ //Carga todas las personas desde el servicio y las agrega al modelo para ser mostradas en la vista persona-list        
        
        //Recuperamos todos los platos de la base de datos
        List<PlatoCategoriaView> platos = platoService.listarTodas()
        //Recorremos los platos y obtenemos su categoría mediante nuestra fábrica Flyweight
        .stream().map(plato -> new PlatoCategoriaView(
            plato,
            categoriaFlyweightFactory.obtener(plato.getCategoria())
        ))
        .collect(Collectors.toList());
        
        //Enviamos a Thymeleaf la nueva lista de objetos PlatoCategoriaView.
        model.addAttribute("platos", platos);

        return "plato-list";
    } 

    @GetMapping("/mostrarPlatos") 
    public String mostrarLogin() {
        return "MostrarPlatos"; 
    }     
    
    @GetMapping("/nuevo") //RECUPERAR DATOS DEL SERVIDOR 
    public String mostrarFormularioNuevaPlato (Model model){
        model.addAttribute("plato",new Plato());//creamos una variable producto que es donde se almacenara cada persona que registremos
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "plato-form";
    }
   
    private static String UPLOAD_DIR = "src/main/resources/static/img/"; // Directorio para guardar imágenes    

    @PostMapping("guardar") //envia datos al servidor   (recibe datos de un formulario)
    public String guardarPlato(@Valid @ModelAttribute Plato plato //GUARDANDO EN MI OBJETO Producto Como un set seria, colocando en mi objeto producto
                                  ,BindingResult result, @RequestParam("foto") MultipartFile foto, @RequestParam("id_categoria") Long idCategoria, HttpSession session){//Creo un parametro producto este producto esta lleno porque antes con mi variable producto que esta arriba de verde ya lo llene, lo que hace este parametro es llevarlo a la db
        
        if (result.hasErrors()) { /*PARA LA VALIDACION*/
            return "plato-form";
        }

        Categoria categoria = categoriaService.obtenerPorId(idCategoria);
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada");
        }
        plato.setCategoria(categoria);
        
        Long idTrabajador = (Long) session.getAttribute("id_trabajador");
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
                
        platoService.guardar(plato);
        return "redirect:/plato";
    }    

    /*Get Mapping es mas que todo para traer datos de la database*/
    @GetMapping("/editar/{id_plato}")//SE MOSTRARA EN LA URL DE MI NAVEGADOR , @PathVariable le dice a spring que tome el id de la URL
    public String mostrarFormularioEditarPlato (@PathVariable Long id_plato,Model model){
        model.addAttribute("plato", platoService.obtenerPorId(id_plato));
        model.addAttribute("categorias", categoriaService.listarTodas());
        
        return "plato-form";
    }
    
    @GetMapping("/eliminar/{id_plato}")
    public String eliminarPlato(@PathVariable Long id_plato){
        platoService.eliminar(id_plato);
        return "redirect:/plato";
    }
    
}
