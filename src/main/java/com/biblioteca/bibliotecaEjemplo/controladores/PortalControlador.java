/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.bibliotecaEjemplo.controladores;

import com.biblioteca.bibliotecaEjemplo.entidades.Usuario;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.servicio.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Damian
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    private UsuarioServicio  usuarioServicio;
    
    @GetMapping("/index.html")
    public String index(){
        return "index.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/autor.html")
    public String autor(){
        return "autor.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/editorial.html")
    public String editorial(){
        return "editorial.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/libros.html")
    public String libro(){
        return "libros.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
        
    }
     @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,@RequestParam String email,@RequestParam String password,@RequestParam String password2,ModelMap modelo){
        try {
           // System.out.println("hola");
            usuarioServicio.regitrar(nombre, email, password, password2);
            modelo.put("exito", "usuario Registrado correctamente");
            return "index.html";
        } catch (MiException ex) {
            //System.out.println("hola desde el eror");
          modelo.put("error", ex.getMessage());
          modelo.put("nombre", nombre);
           modelo.put("email", email);
          return "registrar.html";
        }
    }
    @GetMapping("/login")
    public String login(@RequestParam(required=false)String error,ModelMap modelo){
      if(error!=null){
          modelo.put("error","usuario o contraseña invalido");
      }  
        return "login.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        Usuario logueado=(Usuario) session.getAttribute("usuariosession");
        if (logueado.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/dasboard";
            
        }
        return "inicio.html";
    }
}
