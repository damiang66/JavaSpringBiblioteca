/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.repositorio;

import com.biblioteca.bibliotecaEjemplo.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Damian
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    
}
