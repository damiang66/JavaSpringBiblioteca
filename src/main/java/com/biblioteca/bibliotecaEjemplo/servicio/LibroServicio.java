/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.servicio;

import com.biblioteca.bibliotecaEjemplo.entidades.Autor;
import com.biblioteca.bibliotecaEjemplo.entidades.Editorial;
import com.biblioteca.bibliotecaEjemplo.entidades.Libro;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.repositorio.AutorRepositorio;
import com.biblioteca.bibliotecaEjemplo.repositorio.EditorialRepositorio;
import com.biblioteca.bibliotecaEjemplo.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
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
public class LibroServicio {

       
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, idEditorial, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);

    }

    public List<Libro> listarLibro() {
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();
        return libros;
    }

    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException {
        validar(isbn, titulo, idEditorial, idAutor, idEditorial);
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setTitulo(titulo);
            l.setEjemplares(ejemplares);
            l.setAutor(autor);
            l.setEditorial(editorial);
            libroRepositorio.save(l);
        }

    }
    public Libro BuscarUnLibro(Long isbn){
        return libroRepositorio.getOne(isbn);
    }

    private void validar(Long isbn, String titulo, String ejemplares, String idAutor, String idEditorial) throws MiException {
        if (isbn==null) {
            throw new MiException("el isbn no puede estar vacio");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede estar vacio");
        }
        if (ejemplares == null) {
            throw new MiException("el ejemplar no puede estar vacio");
        }
        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("el autor no puede estar vacio");
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("la Editorial no puede estar vacio");
        }
    }
}
