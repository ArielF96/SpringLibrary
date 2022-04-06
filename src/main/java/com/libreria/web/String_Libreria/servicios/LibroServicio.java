/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.web.String_Libreria.servicios;

import com.libreria.web.String_Libreria.entidades.Autor;
import com.libreria.web.String_Libreria.entidades.Editorial;
import com.libreria.web.String_Libreria.entidades.Libro;
import com.libreria.web.String_Libreria.repositorios.AutorRepositorio;
import com.libreria.web.String_Libreria.repositorios.EditorialRepositorio;
import com.libreria.web.String_Libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepo;

    @Autowired
    private AutorRepositorio autorRepo;

    @Autowired
    private EditorialRepositorio editorialRepo;

    @Transactional
    public Libro crear(Long isbn, String titulo, Integer anio, Integer ejemplares, 
            String autorId, String editorialId) throws Exception {

        validar(isbn, titulo, anio, ejemplares);

        Libro l = new Libro();
        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setAnio(anio);
        l.setEjemplares(ejemplares);
        l.setEjemplaresPrestados(0);
        l.setEjemplaresRestantes(ejemplares);

        Optional<Autor> respuesta = autorRepo.findById(autorId);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            l.setAutor(a);
        } else {
            throw new Exception("No se encontró el autor");
        }

        Optional<Editorial> respuesta2 = editorialRepo.findById(editorialId);
        if (respuesta2.isPresent()) {
            Editorial e = respuesta2.get();
            l.setEditorial(e);
        } else {
            throw new Exception("No se encontró la editorial");
        }

        l.setAlta(true);

        return libroRepo.save(l);
    }

    @Transactional
    public Libro modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, 
            Integer ejemplaresPrestados, String autorId, String editorialId) throws Exception {

        Optional<Libro> respuesta = libroRepo.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setAnio(anio);
            l.setEjemplares(ejemplares);
            l.setEjemplaresPrestados(ejemplaresPrestados);
            l.setEjemplaresRestantes(ejemplares-ejemplaresPrestados);
            
            Optional<Autor> respuesta1 = autorRepo.findById(autorId);
            if (respuesta1.isPresent()) {
                Autor a = respuesta1.get();
                l.setAutor(a);
            } else {
                throw new Exception("No se encontró el autor");
            }

            Optional<Editorial> respuesta2 = editorialRepo.findById(editorialId);
            if (respuesta2.isPresent()) {
                Editorial e = respuesta2.get();
                l.setEditorial(e);
            } else {
                throw new Exception("No se encontró la editorial");
            }

            return libroRepo.save(l);
        } else {
            throw new Exception("No se encontró el libro");
        }
    }
    
    @Transactional
    public Libro prestamo(String id) throws Exception {
        
        Optional<Libro> respuesta = libroRepo.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setEjemplaresPrestados(l.getEjemplaresPrestados()+1);
            l.setEjemplaresRestantes(l.getEjemplares()-l.getEjemplaresPrestados());
            return libroRepo.save(l);
        } else {
            throw new Exception("No se encontró el libro");
        }
    }
    
    @Transactional
    public Libro devolucion(String id) throws Exception {
        
        Optional<Libro> respuesta = libroRepo.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setEjemplaresPrestados(l.getEjemplaresPrestados()-1);
            l.setEjemplaresRestantes(l.getEjemplares()-l.getEjemplaresPrestados());
            return libroRepo.save(l);
        } else {
            throw new Exception("No se encontró el libro");
        }
    }

    @Transactional
    public void eliminar(String id) throws Exception {

        Optional<Libro> respuesta = libroRepo.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setAlta(false);
            libroRepo.save(l);
        } else {
            throw new Exception("No se encontró el libro");
        }
    }
       
    @Transactional(readOnly = true)
    public List<Libro> listar(){
        return libroRepo.listar();
    }
    
    @Transactional(readOnly = true)
    public List<Libro> listarDisponibles(){
        return libroRepo.listarDisponibles();
    }
    
    @Transactional(readOnly = true)
    public Libro buscarPorId(String id){
        return libroRepo.buscarPorId(id);
    } 
    
    @Transactional(readOnly = true)
    public Libro buscarPorTitulo(String titulo){
        return libroRepo.buscarPorTitulo(titulo);
    }
    
    @Transactional(readOnly = true)
    public Libro buscarPorIsbn(Long isbn){
        return libroRepo.buscarPorIsbn(isbn);
    } 
      
    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws Exception {

        if (isbn == null || isbn < 1) {
            throw new Exception("El ISBN es inválido");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("El título es inválido");
        }
        if (anio == null || anio < 1500) {
            throw new Exception("El año es inválido");
        }
        if (ejemplares == null || ejemplares < 1) {
            throw new Exception("La cantidad de ejemplares es inválida");
        }
        if (buscarPorIsbn(isbn) != null) {
            throw new Exception("El libro ya existe en la base de datos");
        }
    }
     
    
}
