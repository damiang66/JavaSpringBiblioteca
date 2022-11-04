/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.bibliotecaEjemplo.servicio;

import com.biblioteca.bibliotecaEjemplo.Enumeraciones.Rol;
import com.biblioteca.bibliotecaEjemplo.entidades.Imagen;
import com.biblioteca.bibliotecaEjemplo.entidades.Usuario;
import com.biblioteca.bibliotecaEjemplo.excepciones.MiException;
import com.biblioteca.bibliotecaEjemplo.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Damian
 */
@Service
public class UsuarioServicio implements UserDetailsService {
   @Autowired
   private UsuarioRepositorio usuarioRepositorio;
   @Autowired
   private ImagenServicio imagenServicio;
   @Transactional
   public void regitrar(MultipartFile archivo, String nombre,String email, String password,String password2) throws MiException{
       validar(nombre, email, password, password2);
       Usuario usuario = new Usuario();
       usuario.setNombre(nombre);
       usuario.setEmail(email);
       usuario.setPassword(new BCryptPasswordEncoder().encode(password));
       usuario.setRol(Rol.USER);
       Imagen imagen = imagenServicio.guardar(archivo);
       usuario.setImagen(imagen);
       usuarioRepositorio.save(usuario);
   }
   public void modificar(MultipartFile archivo ,String idUsuario,String nombre,String email,String password,String password2 ) throws MiException{
       validar(nombre, email, password, password2);
       Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
       if(respuesta.isPresent()){
           Usuario usuario= respuesta.get();
           usuario.setNombre(nombre);
       usuario.setEmail(email);
       usuario.setPassword(new BCryptPasswordEncoder().encode(password));
       usuario.setRol(Rol.USER);
       String idImagen = null;
       if(usuario.getImagen()!=null){
           idImagen=usuario.getImagen().getId();
       }
       Imagen imagen = imagenServicio.actualizar(archivo,idImagen);
       
       usuario.setImagen(imagen);
       
       usuarioRepositorio.save(usuario);
           
       }
   }
   private void validar(String nombre,String email, String password,String password2) throws MiException{
       if (nombre.isEmpty()|| nombre==null){
           throw new MiException("el nombre del usuario no puede estar vacio");
       }
       if (email.isEmpty()|| email==null){
           throw new MiException("el mail del usuario no puede estar vacio");
       }
       if (password.isEmpty()|| password==null||password.length()<=5){
           throw new MiException("la contraseña no puede estar vacia y debe tener mas de 5 digitos");
       }
       if(!password2.equals(password)){
           
            throw new MiException("la contraseñas ingresadas deben ser iguales");
       }
   }
    public Usuario BuscarUnUsuario(String id){
        return usuarioRepositorio.getOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario=usuarioRepositorio.buscarPorEmail(email);
        if(usuario!= null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
                     
            return new User(usuario.getEmail(), usuario.getPassword(),permisos );
        }else{
            return null;
        }
        
    }
   
}
