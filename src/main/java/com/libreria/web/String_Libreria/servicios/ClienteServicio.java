/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.web.String_Libreria.servicios;

import com.libreria.web.String_Libreria.entidades.Cliente;
import com.libreria.web.String_Libreria.repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepo;

    @Transactional
    public Cliente crear(Long documento, String nombre, String apellido, String telefono, String password) throws Exception {

        validar(documento, nombre, apellido, telefono, password);

        Cliente c = new Cliente();
        
        c.setDocumento(documento);
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setTelefono(telefono);
        c.setPassword(password);
        c.setAlta(true);

        return clienteRepo.save(c);
    }

    @Transactional
    public Cliente modificar(String id, Long documento, String nombre, String apellido, String telefono, String password) throws Exception {

        validar(documento, nombre, apellido, telefono, password);

        Optional<Cliente> respuesta = clienteRepo.findById(id);
        if (respuesta.isPresent()) {
            Cliente c = respuesta.get();
            c.setDocumento(documento);
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setTelefono(telefono);
            c.setPassword(password);

            return clienteRepo.save(c);
        } else {
            throw new Exception("No se encontró el cliente");
        }
    }

    @Transactional
    public void eliminar(String id) throws Exception {

        Optional<Cliente> respuesta = clienteRepo.findById(id);
        if (respuesta.isPresent()) {
            Cliente c = respuesta.get();
            c.setAlta(false);
            clienteRepo.save(c);
        } else {
            throw new Exception("No se encontró el cliente");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return clienteRepo.listar();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(String id) {
        return clienteRepo.buscarPorId(id);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorNombre(String nombre) {
        return clienteRepo.buscarPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarPorDocumento(Long documento) {
        return clienteRepo.buscarPorDocumento(documento);
    }
    
    
    
    @Transactional(readOnly = true)
    public Cliente login(Long documento, String password) throws Exception{
        
        if (buscarPorDocumento(documento)!=null) {
            Cliente c = buscarPorDocumento(documento);
            if (password.equals(c.getPassword())) {
                return c;
            } else {
                throw new Exception("Datos incorrectos");
            }
        } return null;
    }
    
    public void validar(Long documento, String nombre, String apellido, String telefono, String password) throws Exception {
        
        if (documento == null || documento < 1) {
            throw new Exception("El documento es inválido");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre es inválido");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("El apellido es inválido");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new Exception("El teléfono es inválido");
        }
        if (password == null || password.isEmpty() || password.length()<3) {
            throw new Exception("La contraseña es inválida");
        }
        if (buscarPorDocumento(documento)!= null) {
            throw new Exception("El DNI ya existe en la base de datos");
        }
    }
}

