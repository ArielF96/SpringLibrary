
package com.libreria.web.String_Libreria.controladores;


import com.libreria.web.String_Libreria.entidades.Autor;
import com.libreria.web.String_Libreria.entidades.Editorial;
import com.libreria.web.String_Libreria.entidades.Libro;
import com.libreria.web.String_Libreria.servicios.AutorServicio;
import com.libreria.web.String_Libreria.servicios.EditorialServicio;
import com.libreria.web.String_Libreria.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;  

    //Lista de libros
    @GetMapping("/lista")
    public String libros(ModelMap modelo) {
        List<Libro> libros = libroServicio.listar();
        modelo.addAttribute("libros", libros);
        return "libros";
    }

    //Formulario para crear libro
    @GetMapping("/formulario")
    public String lFormulario(ModelMap modelo) {   
        List<Autor> autores = autorServicio.listar();
        modelo.addAttribute("autores", autores);
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.addAttribute("editoriales", editoriales);
        return "lFormulario";
    }
    
    //Accion del formulario anterior
    //required = false para que el error sea la excepcion
    @PostMapping("/formulario")
    public String crearLibro(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, 
            @RequestParam(required = false) Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, 
            @RequestParam String autorId, @RequestParam String editorialId, Boolean alta) {
              
        try {
            libroServicio.crear(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorId, editorialId, true);          
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("ejemplaresRestantes", (ejemplares-ejemplaresPrestados)); 
            List<Autor> autores = autorServicio.listar();
            modelo.addAttribute("autores", autores);
            List<Editorial> editoriales = editorialServicio.listar();
            modelo.addAttribute("editoriales", editoriales);
            return"lFormulario";
        }
        modelo.put("exito", "Se ha agregado un libro");
        List<Autor> autores = autorServicio.listar();
        modelo.addAttribute("autores", autores);
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.addAttribute("editoriales", editoriales);
        return "lFormulario";      
    }
    
    //Muestra los datos del libro a editar, en un formulario
    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable String id, ModelMap modelo) {
        Libro libro = libroServicio.buscarPorId(id);
        modelo.addAttribute("libro", libro);
        List<Autor> autores = autorServicio.listar();
        modelo.addAttribute("autores", autores);
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.addAttribute("editoriales", editoriales);
        return "editarLibro";
    }
    
    //Accion del formulario anterior
    @PostMapping("/editar/{id}")
    public String modificarLibro(ModelMap modelo, @PathVariable String id, 
            @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, 
            @RequestParam Integer ejemplaresPrestados, @RequestParam String autorId, @RequestParam String editorialId) {        
        try {           
            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorId, editorialId);               
            modelo.put("exito","Se ha editado un libro");
            List<Libro> libros = libroServicio.listar();
            modelo.addAttribute("libros", libros);
            //return "editarAutor"; //No funciona, error 500 sobre autor.id
            return "libros";
        } catch (Exception ex) { 
            modelo.put("error", ex.getMessage());
            return "libros";
        }            
    }
    
    //Elimina por id, sacada de la tabla
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable String id, ModelMap modelo) {
        try {
            libroServicio.eliminar(id);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }
        modelo.put("exito","Se ha eliminado un libro");
        List<Libro> libros = libroServicio.listar();
        modelo.addAttribute("libros", libros);
        return "libros";
    }
}
