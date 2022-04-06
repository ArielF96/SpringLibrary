/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.libreria.web.String_Libreria.servicios;

import com.libreria.web.String_Libreria.entidades.Editorial;
import com.libreria.web.String_Libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepo;

    @Transactional
    public Editorial crear(String nombre, Boolean alta) throws Exception {

        validar(nombre);

        Editorial e = new Editorial();

        e.setNombre(nombre);
        e.setAlta(true);

        return editorialRepo.save(e);
    }
    
    @Transactional
    public Editorial modificar(String id, String nombre)throws Exception{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepo.findById(id);
        if (respuesta.isPresent()) {
            Editorial e = respuesta.get();            
            e.setNombre(nombre);
            
            return editorialRepo.save(e);
        } else {
            throw new Exception("No se encontró la editorial");       
        }
    }
    
    @Transactional
    public void eliminar(String id)throws Exception{
        
        Optional<Editorial> respuesta = editorialRepo.findById(id);
        if (respuesta.isPresent()) {
            Editorial e = respuesta.get();
            e.setAlta(false);
            editorialRepo.save(e);
        } else {
            throw new Exception("No se encontró la editorial");       
        }
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listar(){
        return editorialRepo.listar();
    }
    
    @Transactional(readOnly = true)
    public Editorial buscarPorId(String id){
        return editorialRepo.buscarPorId(id);
    }
    
    @Transactional(readOnly = true)
    public Editorial buscarPorNombre(String nombre){
        return editorialRepo.buscarPorNombre(nombre);
    }
    
    public void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre es inválido");
        }   
        if (buscarPorNombre(nombre) != null) {
            throw new Exception("La editorial ya existe");
        }
    }
}

