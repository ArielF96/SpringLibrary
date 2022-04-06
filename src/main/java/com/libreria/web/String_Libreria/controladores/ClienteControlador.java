
package com.libreria.web.String_Libreria.controladores;

import com.libreria.web.String_Libreria.entidades.Cliente;
import com.libreria.web.String_Libreria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/formulario")
    public String cFormulario() {
        return "cFormulario";
    }
    
    
    @PostMapping("/formulario")
    public String crearCliente(ModelMap modelo, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String password) {

        try {
            clienteServicio.crear(documento, nombre, apellido, telefono, password);
            modelo.put("exito", "Se ha registrado correctamente");
            return "cFormulario";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("documento", documento);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            return "cFormulario";
        }     
    }
    
//    @GetMapping("/login")
//    public String login() {
//        return "cLogin";
//    }
//    
//    @PostMapping("/login")
//    public String loginFormulario(ModelMap modelo, @RequestParam Long documento, @RequestParam String password) {
//        try {
//            clienteServicio.login(documento, password);
//            modelo.put("exito", "Se ha iniciado sesión correctamente");
//            return "cLogin";
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//            modelo.put("documento", documento);
//            modelo.put("password", password);
//            return "cLogin";
//        }
//       
//    } 
    
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable String id, ModelMap modelo) {
        Cliente cliente = clienteServicio.buscarPorId(id);
        modelo.addAttribute("cliente", cliente);
        
        return "editarCliente";
    }
    
    
//    @PostMapping("/editar/{id}")
//    public String modificarAutor(ModelMap modelo, @PathVariable String id, @RequestParam String apellido, @RequestParam String nombre) {        
//        try {           
//            autorServicio.modificar(id, nombre, apellido);               
//            modelo.put("exito","Se ha editado un autor");
//            List<Autor> autores = autorServicio.listar();
//            modelo.addAttribute("autores", autores);            
//            return "autores";
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//            List<Autor> autores = autorServicio.listar();
//            modelo.addAttribute("autores", autores);
//            return "autores";
//        }      
//        
//    }
//    
//    //Elimina por id, sacada de la tabla
//    @GetMapping("/eliminar/{id}")
//    public String eliminarAutor(@PathVariable String id, ModelMap modelo) {
//        try {
//            autorServicio.eliminar(id);
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//        }
//        modelo.put("exito","Se ha eliminado un autor");
//        List<Autor> autores = autorServicio.listar();
//        modelo.addAttribute("autores", autores);
//        return "autores";
//    }
}
