/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.controladores;


import com.biblioteca.bibliotecaEjemplo.entidades.Editorial;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.repositorio.EditorialRepositorio;
import com.biblioteca.bibliotecaEjemplo.servicio.EditorialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    EditorialServicio editorialServicio;
    @GetMapping("/registrar")
    public String registrar(){
        return "cargaEditoriales.html";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre){
        try {
            editorialServicio.crearEditorial(nombre);
        } catch (MiException ex) {
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "cargaEditoriales.html";
        }
        return "cargaEditoriales.html";
    }
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Editorial> editorial = editorialServicio.listarEditorial();
        modelo.addAttribute("editoriales", editorial);
        modelo.getAttribute("id");
        return "listaEditoriales.html";
        
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombre,ModelMap modelo){
        modelo.put("editorial", editorialServicio.MostrarUnSoloAutor(id));
        return "editorialModificar.html";
        
    }
    @PostMapping("/modificar/{id}")
    public String modifica(@PathVariable String id,String nombre,ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorialModificar.html";
            
        }
      
    }
}
