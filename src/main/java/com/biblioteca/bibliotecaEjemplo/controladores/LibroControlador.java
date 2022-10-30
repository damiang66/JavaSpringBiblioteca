/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.controladores;

import com.biblioteca.bibliotecaEjemplo.entidades.Autor;
import com.biblioteca.bibliotecaEjemplo.entidades.Editorial;
import com.biblioteca.bibliotecaEjemplo.entidades.Libro;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.servicio.AutorServicio;
import com.biblioteca.bibliotecaEjemplo.servicio.EditorialServicio;
import com.biblioteca.bibliotecaEjemplo.servicio.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Damian
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private AutorServicio autorServicio;
    
     @GetMapping("/registrar")
     public String registrar(ModelMap modelo){
         List<Autor> autores= autorServicio.listarAutor();
         List<Editorial>editoriales=editorialServicio.listarEditorial();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
         
         return "cargaLibros.html"; 
     }
     @PostMapping("/registro")
     public String registro(@RequestParam(required=false) Long isbn,@RequestParam String titulo
             ,@RequestParam(required = false) Integer ejemplares, 
             @RequestParam String idAutor,@RequestParam String idEditorial,ModelMap modelo){
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "el libro fue cargado correctamente");
        } catch (MiException ex) {
            System.out.println(ex.getMessage());
            modelo.put("error", ex.getMessage());
              List<Autor> autores= autorServicio.listarAutor();
         List<Editorial>editoriales=editorialServicio.listarEditorial();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
//Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);
              return "cargaLibros.html";
        }
         return "cargaLibros.html";
     }
     @GetMapping("/lista")
     public String listar(ModelMap modelo){
         List<Libro> libros = libroServicio.listarLibro();
         modelo.addAttribute("libros", libros);
         return "listaLibro.html";
     }
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long id,ModelMap modelo){
        modelo.put("libro", libroServicio.BuscarUnLibro(id));
        System.out.println(modelo.toString());
        return "libroModificar.html";
        
    }
    
}
