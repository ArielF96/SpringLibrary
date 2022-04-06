/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.web.String_Libreria.controladores;

import com.libreria.web.String_Libreria.entidades.Cliente;
import com.libreria.web.String_Libreria.entidades.Libro;
import com.libreria.web.String_Libreria.entidades.Prestamo;
import com.libreria.web.String_Libreria.servicios.ClienteServicio;
import com.libreria.web.String_Libreria.servicios.LibroServicio;
import com.libreria.web.String_Libreria.servicios.PrestamoServicio;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {

    @Autowired
    private PrestamoServicio prestamoServicio;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/lista")
    public String prestamos(ModelMap modelo) {
        List<Prestamo> prestamos = prestamoServicio.listar();
        modelo.addAttribute("prestamos", prestamos);
        return "prestamos";
    }

    @GetMapping("/formulario")
    public String pFormulario(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarDisponibles();
        modelo.addAttribute("libros", libros);
        List<Cliente> clientes = clienteServicio.listar();
        modelo.addAttribute("clientes", clientes);
        //fecha actual, sin hora
        LocalDate hoy = LocalDate.now();
        modelo.addAttribute("hoy", hoy);
        //suma una semana, parametro definido
        LocalDate semana = hoy.plusDays(7);
        modelo.addAttribute("semana", semana);
        return "pFormulario";

    }

    @PostMapping("/formulario")
    public String crearPrestamo(ModelMap modelo, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date fechaPrestamo, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date fechaDevolucion, @RequestParam String libroId, @RequestParam String clienteId) {

        try {
            prestamoServicio.crear(fechaPrestamo, fechaDevolucion, libroId, clienteId);
            modelo.put("exito", "Se ha registrado su préstamo correctamente");
            List<Libro> libros = libroServicio.listarDisponibles();
            modelo.addAttribute("libros", libros);
            List<Cliente> clientes = clienteServicio.listar();
            modelo.addAttribute("clientes", clientes);
            return "pFormulario";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            List<Libro> libros = libroServicio.listarDisponibles();
            modelo.addAttribute("libros", libros);
            List<Cliente> clientes = clienteServicio.listar();
            modelo.addAttribute("clientes", clientes);
            modelo.addAttribute("clientes", clientes);
            //fecha actual, sin hora
            LocalDate hoy = LocalDate.now();
            modelo.addAttribute("hoy", hoy);
            //suma una semana, parametro definido
            LocalDate semana = hoy.plusDays(7);
            modelo.addAttribute("semana", semana);
            return "pFormulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarPrestamo(@PathVariable String id, ModelMap modelo) {
        Prestamo prestamo = prestamoServicio.buscarPorId(id);
        modelo.addAttribute("prestamo", prestamo);
        List<Cliente> clientes = clienteServicio.listar();
        modelo.addAttribute("clientes", clientes);
        List<Libro> libros = libroServicio.listar();
        modelo.addAttribute("libros", libros);
        return "editarPrestamo";
    }

    //Accion del formulario anterior
    @PostMapping("/editar/{id}")
    public String modificarPrestamo(ModelMap modelo, @PathVariable String id, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam Date fechaDevolucion, @RequestParam String libroId, @RequestParam String clienteId) {
        try {
            prestamoServicio.modificar(id, fechaDevolucion, libroId, clienteId);
            modelo.put("exito", "Se ha editado un préstamo");
            List<Prestamo> prestamos = prestamoServicio.listar();
            modelo.addAttribute("prestamos", prestamos);
            //return "editarAutor"; //No funciona, error 500 sobre autor.id
            return "prestamos";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "prestamos";
        }
    }

    //Elimina por id, sacada de la tabla
    @GetMapping("/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable String id, ModelMap modelo) {
        try {
            prestamoServicio.eliminar(id);
            modelo.put("exito", "Se ha terminado un préstamo");
            List<Prestamo> prestamos = prestamoServicio.listar();
            modelo.addAttribute("prestamos", prestamos);
            return "prestamos";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "prestamos";
        }
    }

}
