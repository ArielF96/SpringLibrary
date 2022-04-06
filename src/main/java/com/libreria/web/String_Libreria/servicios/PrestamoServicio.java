/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.web.String_Libreria.servicios;

import com.libreria.web.String_Libreria.entidades.Autor;
import com.libreria.web.String_Libreria.entidades.Cliente;
import com.libreria.web.String_Libreria.entidades.Editorial;
import com.libreria.web.String_Libreria.entidades.Libro;
import com.libreria.web.String_Libreria.entidades.Prestamo;
import com.libreria.web.String_Libreria.repositorios.ClienteRepositorio;
import com.libreria.web.String_Libreria.repositorios.LibroRepositorio;
import com.libreria.web.String_Libreria.repositorios.PrestamoRepositorio;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepo;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private LibroRepositorio libroRepo;

    @Autowired
    private ClienteRepositorio clienteRepo;

    @Transactional
    public Prestamo crear(Date fechaPrestamo, Date fechaDevolucion, String libroId, String clienteId) throws Exception {

        validar(fechaPrestamo, fechaDevolucion);
        Prestamo p = new Prestamo();

        p.setFechaPrestamo(fechaPrestamo);
        p.setFechaDevolucion(fechaDevolucion);

        Optional<Libro> respuesta = libroRepo.findById(libroId);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            p.setLibro(l);
            libroServicio.prestamo(libroId);
        } else {
            throw new Exception("No se encontró el libro");
        }

        Optional<Cliente> respuesta2 = clienteRepo.findById(clienteId);
        if (respuesta2.isPresent()) {
            Cliente c = respuesta2.get();
            p.setCliente(c);
        } else {
            throw new Exception("No se encontró el cliente");
        }
        
//        Libro l = libroServicio.buscarPorId(libroId);
//        p.setLibro(l);

//        Cliente c = clienteServicio.buscarPorId(clienteId);
//        p.setCliente(c);
        p.setAlta(true);

        return prestamoRepo.save(p);
    }

    @Transactional
    public Prestamo modificar(String id, Date fechaDevolucion, String libroId, String clienteId) throws Exception {

        Optional<Prestamo> respuesta = prestamoRepo.findById(id);
        if (respuesta.isPresent()) {
            Prestamo p = respuesta.get();
            p.setFechaDevolucion(fechaDevolucion);

            Optional<Libro> respuesta1 = libroRepo.findById(libroId);
            if (respuesta1.isPresent()) {
                Libro l = respuesta1.get();
                p.setLibro(l);
            } else {
                throw new Exception("No se encontró el libro");
            }

            Optional<Cliente> respuesta2 = clienteRepo.findById(clienteId);
            if (respuesta2.isPresent()) {
                Cliente c = respuesta2.get();
                p.setCliente(c);
            } else {
                throw new Exception("No se encontró el cliente");
            }         
            return prestamoRepo.save(p);
        } else {
            throw new Exception("No se encontró el prestamo");
        }
    }

    @Transactional
    public void eliminar(String id) throws Exception {
        Optional<Prestamo> respuesta = prestamoRepo.findById(id);
        if (respuesta.isPresent()) {
            Prestamo p = respuesta.get();
            String libroId = p.getLibro().getId();
            libroServicio.devolucion(libroId);
            p.setAlta(false);
            prestamoRepo.save(p);
        } else {
            throw new Exception("No se encontró el prestamo");
        }
    }
       
    @Transactional(readOnly = true)
    public List<Prestamo> listar() {
        return prestamoRepo.listar();
    }

    @Transactional(readOnly = true)
    public Prestamo buscarPorId(String id) {
        return prestamoRepo.buscarPorId(id);
    }
//    
//    @Transactional(readOnly = true)
//    public Libro buscarPorTitulo(String titulo){
//        return libroRepo.buscarPorTitulo(titulo);
//    }

    public void validar(Date fechaPrestamo, Date fechaDevolucion) throws Exception {            
        if (fechaPrestamo == null) {
            throw new Exception("La fecha de préstamo es inválida");
        }
        if (fechaDevolucion == null) {
            throw new Exception("La fecha de devolución es inválida");
        }
        if (fechaDevolucion.before(fechaPrestamo)) {
            throw new Exception("La fecha de devolución tiene que ser posterior a la de préstamo");
        } 
        if (fechaDevolucion.equals(fechaPrestamo)) {
            throw new Exception("Las fechas no pueden ser iguales");
        }
    }
}
