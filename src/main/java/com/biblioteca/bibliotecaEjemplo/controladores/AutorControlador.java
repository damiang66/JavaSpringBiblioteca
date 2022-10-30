/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.controladores;

import com.biblioteca.bibliotecaEjemplo.entidades.Autor;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.servicio.AutorServicio;
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
@RequestMapping("/autor")//localhost:8080/autor
public class AutorControlador {
    @Autowired
    public AutorServicio autorServicio;
@GetMapping("/registrar")//localhost:8080/autor/registrar
public String registrar(){
    return "cargaAutores.html";
}

    /**
     *
     * @param nombre
     * @return
     *
     */
    @PostMapping("/registro")
public String registro(@RequestParam String nombre,ModelMap modelo){
        try {
            System.out.println("nombre: "+ nombre);
           
            //System.out.println(nombre);
            autorServicio.crearAutor(nombre);
             modelo.put("exito", "Autor cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
           // Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "cargaAutores.html";
        }
  return "cargaAutores.html";
    
}
@GetMapping("/lista")
public String listar(ModelMap modelo){
    List<Autor> autores = autorServicio.listarAutor();
    modelo.addAttribute("autores", autores);
   modelo.getAttribute("id");
     
    return "listaAutores.html";
}
@GetMapping("/modificar/{id}")
public String modificar(@PathVariable String id, ModelMap modelo ){
    modelo.put("autor", autorServicio.motrarUnAutor(id));
    return "autorModificar.html";
}
@PostMapping("/modificar/{id}")
public String modificar(@PathVariable String id,String nombre,ModelMap modelo){
        try {
            autorServicio.modificarAutor(id, nombre);
            return "redirect:../lista";
        } catch (MiException ex) {
           // Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
           modelo.put("error", ex.getMessage());
           return "autorModificar.html";
        }
}
}
