/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Damian
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    @GetMapping("/index.html")
    public String index(){
        return "index.html";
    }
    @GetMapping("/autor.html")
    public String autor(){
        return "autor.html";
    }
    @GetMapping("/editorial.html")
    public String editorial(){
        return "editorial.html";
    }
    @GetMapping("/libros.html")
    public String libro(){
        return "libros.html";
    }
  
}
