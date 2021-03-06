/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.libreria.web.String_Libreria.repositorios;

import com.libreria.web.String_Libreria.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>  {
    
    @Query("SELECT c FROM Cliente c WHERE c.alta = 1 ORDER BY c.apellido")
    public List<Cliente> listar();
    
    @Query("SELECT c FROM Cliente c WHERE c.id = :id")
    public Cliente buscarPorId(@Param("id") String id);
    
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    public Cliente buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Cliente c WHERE c.documento = :documento")
    public Cliente buscarPorDocumento(@Param("documento") Long documento);
}
