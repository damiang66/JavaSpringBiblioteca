/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.servicio;

import com.biblioteca.bibliotecaEjemplo.entidades.Autor;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.repositorio.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

/**
 *
 * @author Damian
 */
@Service
public class AutorServicio {
 @Autowired
AutorRepositorio autorRepositorio ;
    
          

  @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validar(nombre);
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
    //    System.out.println("estoy en el servicio"+autor.getNombre());
        
       autorRepositorio.save(autor);
        
    }
    public List<Autor> listarAutor(){
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }
    public void modificarAutor(String id, String nombre) throws MiException{
        validar(nombre);
        Optional<Autor>respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
        }
                
    } 
 public Autor motrarUnAutor(String id){
     return autorRepositorio.getOne(id);
 }   
    
    
private void validar(String nombre) throws MiException{
        if (nombre.isEmpty()||nombre==null){
            throw new MiException("el nombre no puede estar vacio");
        }
}
}
