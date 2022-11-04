/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.servicio;


import com.biblioteca.bibliotecaEjemplo.entidades.Editorial;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.repositorio.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Damian
 */
@Service
public class EditorialServicio {
    @Autowired 
    
    EditorialRepositorio editorialRepositorio;
      @Transactional
    public void crearEditorial(String nombre) throws MiException{
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }
      public List<Editorial> listarEditorial(){
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
      public void modificarEditorial(String id,String nombre) throws MiException{
          validar(nombre);
          Optional<Editorial> respuesta = editorialRepositorio.findById(id);
          if(respuesta.isPresent()){
              Editorial editorial = respuesta.get();
              editorial.setNombre(nombre);
              editorialRepositorio.save(editorial);
          }
          
      }
      public Editorial MostrarUnSoloAutor(String id){
          Editorial editorial=editorialRepositorio.getOne(id);
          return editorial;
      }
      public void validar(String nombre) throws MiException{
          if(nombre.isEmpty()|| nombre==null){
              throw new MiException("el nombre de la editorial no puede estar vacia");
          }
      }
}
