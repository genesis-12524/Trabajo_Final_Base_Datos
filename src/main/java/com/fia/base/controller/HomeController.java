package com.fia.base.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fia.base.model.DTO.UsuarioLoginDto;
import com.fia.base.model.dao.IDocente;
import com.fia.base.model.dao.IRol;
import com.fia.base.model.dao.IUsuario;
import com.fia.base.model.entity.Docente;
import com.fia.base.model.entity.Rol;
import com.fia.base.model.entity.Usuario;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class HomeController {
	 private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private IUsuario usuarioRepository;

	@Autowired
	private IDocente docenteRepository;
	@Autowired
    private IRol rolRepository;
    @GetMapping("/")
    public String inicio() {
        return "Index";
    }
    
    @GetMapping("/seleccionar-rol")
    public String mostrarRolesParaLogin(Model model) {
        List<Rol> roles = rolRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("titulo", "Iniciar sesión como:");
        
        return "OpcionesRol";
    }
	
    @GetMapping("/login")
    public String procesarSeleccionRol(
            @RequestParam("rolId") Long rolId, 
            Model model) {
        
        Rol rolSeleccionado = rolRepository.findById(rolId).orElseThrow();
        
        model.addAttribute("titulo", "Iniciar sesión como " + rolSeleccionado.getNombre());
        model.addAttribute("UsuarioLoginDto", new UsuarioLoginDto()); // Cambiado de "usuario" a "UsuarioLoginDto"
        model.addAttribute("rolNombre", rolSeleccionado.getNombre());
        model.addAttribute("rolId", rolId); 
        
        return "inicioSesion";
    }
    @PostMapping("/procesar-login")
    public String procesarLogin(
            @Valid UsuarioLoginDto usuarioLoginDto,
            @RequestParam("rolId") Long rolId,
            BindingResult result,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {  // Añadido RedirectAttributes

        logger.info("Intento de login para correo: {}, rolId: {}", 
                   usuarioLoginDto.getCorreo(), rolId);

        // 1. Determinar primero qué tipo de rol se está solicitando
        Optional<Rol> rolOpt = rolRepository.findById(rolId);
        if (rolOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Rol no válido");
            return "redirect:/login?rolId=" + rolId;
        }

        String nombreRol = rolOpt.get().getNombre();
        boolean esRolDocente = "Docente".equalsIgnoreCase(nombreRol);

        // 2. Buscar según el tipo de rol solicitado
        if (esRolDocente) {
            Optional<Docente> docenteOpt = docenteRepository.findByCorreo(usuarioLoginDto.getCorreo());
            if (docenteOpt.isPresent()) {
                Docente docente = docenteOpt.get();
                if (docente.getContrasena().equals(usuarioLoginDto.getContrasena())) {
                    if (docente.getRol().getId().equals(rolId)) {
                        session.setAttribute("usuario", docente);
                        logger.info("Login exitoso como DOCENTE: {}", docente.getCorreo());
                        return "redirect:/usuario/home";
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Rol no coincide para docente");
                    }
                } else {
                    redirectAttributes.addFlashAttribute("error", "Contraseña incorrecta para docente");
                }
                return "redirect:/login?rolId=" + rolId;
            }
        }

        // 3. Si no es rol docente o no encontró docente, buscar como usuario normal
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(usuarioLoginDto.getCorreo());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getContrasena().equals(usuarioLoginDto.getContrasena())) {
                if (usuario.getRol().getId().equals(rolId)) {
                    session.setAttribute("usuario", usuario);
                    logger.info("Login exitoso como USUARIO: {}", usuario.getCorreo());
                    
                    if ("Departamento Académico".equalsIgnoreCase(usuario.getRol().getNombre())) {
                        return "redirect:/admin/home";
                    } else {
                        return "redirect:/usuario/home";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("error", "Rol no coincide para usuario");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Contraseña incorrecta para usuario");
            }
            return "redirect:/login?rolId=" + rolId;
        }

        // 4. Si no se encontró en ninguna tabla
        redirectAttributes.addFlashAttribute("error", "Credenciales no válidas");
        return "redirect:/login?rolId=" + rolId;
    }
    
}
