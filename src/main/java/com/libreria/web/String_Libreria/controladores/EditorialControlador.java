
package com.libreria.web.String_Libreria.controladores;

import com.libreria.web.String_Libreria.entidades.Editorial;
import com.libreria.web.String_Libreria.repositorios.EditorialRepositorio;
import com.libreria.web.String_Libreria.servicios.EditorialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    //Si esta en dos controladores no funciona correctamente
//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }
    
    //Lista de autores
    @GetMapping("/lista")
    public String editoriales(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.addAttribute("editoriales", editoriales);
        return "editoriales";
    }

    //Formulario para crear Autor
    @GetMapping("/formulario")
    public String eFormulario() {
        return "eFormulario";
    }
    
    //Accion del formulario anterior
    @PostMapping("/formulario")
    public String crearEditorial(ModelMap modelo, @RequestParam String nombre, Boolean alta) {

        try {
            editorialServicio.crear(nombre, true);           
            modelo.put("exito", "Se ha agregado una editorial");
            return "eFormulario";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "eFormulario";
        }
        
    }
    
    //Muestra los datos del autor a editar, en un formulario
    @GetMapping("/editar/{id}")
    public String editarEditorial(@PathVariable String id, ModelMap modelo) {
        Editorial editorial = editorialServicio.buscarPorId(id);
        modelo.addAttribute("editorial", editorial);
        return "editarEditorial";
    }
    
    //Accion del formulario anterior
    @PostMapping("/editar/{id}")
    public String modificarEditorial(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {        
        try {           
            editorialServicio.modificar(id, nombre);               
            modelo.put("exito","Se ha editado una editorial");
            List<Editorial> editoriales = editorialServicio.listar();
            modelo.addAttribute("editoriales", editoriales);
            //return "editarAutor"; //No funciona, error 500 sobre autor.id
            return "editoriales";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            List<Editorial> editoriales = editorialServicio.listar();
            modelo.addAttribute("editoriales", editoriales);
            return "editoriales";
        }      
        
    }
    
    //Elimina por id, sacada de la tabla
    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(@PathVariable String id, ModelMap modelo) {
        try {
            editorialServicio.eliminar(id);
        } catch (Exception ex) {
             modelo.put("error", ex.getMessage());
        }
        modelo.put("exito","Se ha eliminado una editorial");
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.addAttribute("editoriales", editoriales);
        return "editoriales";
    }

}

