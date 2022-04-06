/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.libreria.web.String_Libreria.repositorios;

import com.libreria.web.String_Libreria.entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>  {
    
    @Query("SELECT c FROM Prestamo c WHERE c.alta = 1 ORDER BY c.fechaDevolucion")
    public List<Prestamo> listar();
    
    @Query("SELECT c FROM Prestamo c WHERE c.id = :id")
    public Prestamo buscarPorId(@Param("id") String id);
    
//    @Query("SELECT c FROM Prestamo c WHERE c.nombre = :nombre")
//    public Prestamo buscarPorNombre(@Param("nombre") String nombre);
}
