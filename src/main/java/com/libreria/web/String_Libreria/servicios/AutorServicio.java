/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.web.String_Libreria.servicios;

import com.libreria.web.String_Libreria.entidades.Autor;
import com.libreria.web.String_Libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepo;

    @Transactional
    public Autor crear(String nombre, String apellido, Boolean alta) throws Exception {

        validar(nombre, apellido);

        Autor a = new Autor();

        a.setNombre(nombre);
        a.setApellido(apellido);
        a.setAlta(true);

        return autorRepo.save(a);
    }
    
    @Transactional
    public Autor modificar(String id, String nombre, String apellido)throws Exception{
        
        validar(nombre, apellido);
        
        Optional<Autor> respuesta = autorRepo.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();            
            a.setNombre(nombre);
            a.setApellido(apellido);
            
            return autorRepo.save(a);
        } else {
            throw new Exception("No se encontró el autor");       
        }
    }
    
    @Transactional
    public void eliminar(String id)throws Exception{       
        Optional<Autor> respuesta = autorRepo.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setAlta(false);
            autorRepo.save(a);
        } else {
            throw new Exception("No se encontró el autor");       
        }
    }
    
    @Transactional
    public void darAlta(String id)throws Exception{      
        Optional<Autor> respuesta = autorRepo.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setAlta(true);
            autorRepo.save(a);
        } else {
            throw new Exception("No se encontró el autor");       
        }
    }
    
    @Transactional
    public void eliminarDefinitivamente(String id)throws Exception{      
        Optional<Autor> respuesta = autorRepo.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setAlta(true);
            autorRepo.delete(a);
        } else {
            throw new Exception("No se encontró el autor");       
        }
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listar(){
        return autorRepo.listar();
    }   
    
    @Transactional(readOnly = true)
    public List<Autor> listarEliminados(){
        return autorRepo.listarEliminados();
    }   
    
    @Transactional(readOnly = true)
    public Autor buscarPorId(String id){
        return autorRepo.buscarPorId(id);
    }
    
    @Transactional(readOnly = true)
    public Autor buscarPorNombre(String nombre){
        return autorRepo.buscarPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public Autor buscarPorApellido(String apellido){
        return autorRepo.buscarPorApellido(apellido);
    }
    
    public void validar(String nombre, String apellido) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre es inválido");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("El apellido es inválido");
        }
        if (buscarPorNombre(nombre) != null && buscarPorApellido(apellido) != null) {
            throw new Exception("El autor ya existe en la base de datos");
        }   
    }
}
