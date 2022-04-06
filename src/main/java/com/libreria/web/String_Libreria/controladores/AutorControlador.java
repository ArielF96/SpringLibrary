
package com.libreria.web.String_Libreria.controladores;

import com.libreria.web.String_Libreria.entidades.Autor;
import com.libreria.web.String_Libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    //Lista de autores
    @GetMapping("/lista")
    public String autores(ModelMap modelo) {
        List<Autor> autores = autorServicio.listar();
        modelo.put("autores", autores);
        return "autores";
    }   
    
//  Formulario para crear Autor
    @GetMapping("/formulario")
    public String aFormulario() {
        return "aFormulario";
    }
    
    //Accion del formulario anterior
    @PostMapping("/formulario")
    public String crearAutor(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, Boolean alta) {

        try {
            autorServicio.crear(nombre, apellido, true);
            modelo.put("exito", "Se ha agregado un autor");
            return "aFormulario";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            return "aFormulario";
        }
        
    }
    
    //Muestra los datos del autor a editar, en un formulario
    @GetMapping("/editar/{id}")
    public String editarAutor(@PathVariable String id, ModelMap modelo) {
        Autor autor = autorServicio.buscarPorId(id);
        modelo.addAttribute("autor", autor);
        return "editarAutor";
    }
    
    //Accion del formulario anterior
    @PostMapping("/editar/{id}")
    public String modificarAutor(ModelMap modelo, @PathVariable String id, @RequestParam String apellido, @RequestParam String nombre) {        
        try {           
            autorServicio.modificar(id, nombre, apellido);               
            modelo.put("exito","Se ha editado un autor");
            List<Autor> autores = autorServicio.listar();
            modelo.addAttribute("autores", autores);            
            return "autores";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            List<Autor> autores = autorServicio.listar();
            modelo.addAttribute("autores", autores);
            return "autores";
        }      
        
    }
    
    //Elimina por id, sacada de la tabla
    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable String id, ModelMap modelo) {
        try {
            autorServicio.eliminar(id);
            modelo.put("exito","Se ha eliminado un autor");
            List<Autor> autores = autorServicio.listar();
            modelo.addAttribute("autores", autores);
            return "autores";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "autores";
        }       
    }
    
    @GetMapping("/lista-eliminados")
    public String autoresEliminados(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarEliminados();
        modelo.put("autores", autores);
        return "autoresEliminados";
    }
    
    @GetMapping("/dar-alta/{id}")
    public String altaAutor(@PathVariable String id, ModelMap modelo) {
        try {
            autorServicio.darAlta(id);
            modelo.put("exito","Se ha dado de alta un autor");
            List<Autor> autores = autorServicio.listar();
            modelo.addAttribute("autores", autores);
            return "autores";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "autores";
        }       
    }
    
    @GetMapping("/eliminar-definitivamente/{id}")
    public String eliminarAutorDefinitivamente(@PathVariable String id, ModelMap modelo) {
        try {
            autorServicio.eliminarDefinitivamente(id);
            modelo.put("exito","Se ha eliminado un autor");
            List<Autor> autores = autorServicio.listarEliminados();
            modelo.addAttribute("autores", autores);
            return "autoresEliminados";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "autoresEliminados";
        }       
    }
    
    
}
