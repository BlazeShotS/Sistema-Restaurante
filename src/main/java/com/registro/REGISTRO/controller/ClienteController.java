package com.registro.REGISTRO.controller;


import com.registro.REGISTRO.model.Cliente;
import com.registro.REGISTRO.model.Trabajador;
import com.registro.REGISTRO.service.ClienteService;
import com.registro.REGISTRO.service.TrabajadorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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



@Controller
@RequestMapping("/cliente")
public class ClienteController {
    
    
    @Autowired
    private ClienteService clienteService;        

    @Autowired
    private TrabajadorService trabajadorService;
    
    
    @GetMapping("/loginCliente") 
    public String mostrarLogin() {
        return "ClienteLogin"; 
    }
    
    
    @GetMapping  // aca le digo que /cliente es clientes-list (POR DEFECTO)
    public String listarCliente(Model model){ 
        model.addAttribute("clientes",clienteService.listarTodas()); //el clienteService se encarga del manejo y logica con la base de datos
        return "clientes-list";
    }  
    
    
    /*Para crear una nueva producto*/  /*LLENA EL NAVEGADOR, MUESTRA EN EL NAVEGADOR*/
    @GetMapping("/nuevo") //RECUPERAR DATOS DEL SERVIDOR 
    public String mostrarFormularioNuevaCliente (Model model){
        model.addAttribute("cliente",new Cliente());//creamos una variable producto que es donde se almacenara cada persona que registremos
        return "clientes-form";
    }
    
    
    
    @PostMapping("guardar") //envia datos al servidor   (recibe datos de un formulario)
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente //GUARDANDO EN MI OBJETO Cliente Como un set seria, colocando en mi objeto cliente
                                  ,BindingResult result                                  
                                  ,HttpSession session){
        
        if (result.hasErrors()) { /*PARA LA VALIDACION*/
            return "clientes-form";
        }
                                                             
        clienteService.guardar(cliente); //Guarda el objeto Cliente en la base de datos 
        return "redirect:/pedido/platos";
    }
    
    /*
    @GetMapping("/editar/{id_cliente}")//SE MOSTRARA EN LA URL DE MI NAVEGADOR , @PathVariable le dice a spring que tome el id de la URL
    public String mostrarFormularioEditarCliente (@PathVariable Long id_cliente,Model model){
        model.addAttribute("cliente", clienteService.obtenerPorId(id_cliente));
        return "clientes-form";
    }*/
    
    
    
    /*----CUANDO INICIA SESION , VER SI LAS CREDENCIALES SON CORRECTAS-----*/
    @PostMapping("login")
    public String iniciarSesion(@RequestParam("id") String id, @RequestParam("contrasena") String contrasena, HttpSession session, Model model) {
        // Verificar si el cliente existe con el id (DNI) y la contraseña
        Cliente cliente = clienteService.obtenerPorIdLogin(id);  // Ahora usamos el id como parámetro
        
        if (cliente != null && cliente.getPassword().equals(contrasena)) {
            // Guardar el id_cliente en la sesión si la autenticación es exitosa
            session.setAttribute("id_cliente", cliente.getIdCliente());  // Guardamos el id_cliente en la sesión
            // Redirigir al carrito o la página principal
            session.setAttribute("nombre_cliente", cliente.getNombre());
            return "redirect:/pedido/platos";
        } else {
            // Si las credenciales son incorrectas, mostrar mensaje de error
            model.addAttribute("error", "ID o contraseña incorrectos.");
            return "ClienteLogin";  // Redirigir al formulario de login cliente nuevamente
        }
    }
    
    
    
    @GetMapping("/eliminar/{id_cliente}")
    public String eliminarCliente(@PathVariable Long id_cliente){
        clienteService.eliminar(id_cliente);
        return "redirect:/cliente";
    }
    
    
    @PostMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session) {
        // Eliminar el id_cliente de la sesión
        session.invalidate();
        return "redirect:/cliente/loginCliente";  //Redirigir, al hace esto pierde los datos y no hay nadie logeado ahora
    }
    
    
}
