package Grupo_5.UTNC.controller;

import Grupo_5.UTNC.service.CursoService;
import Grupo_5.UTNC.service.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalController {
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public PortalController(EstudianteService estudianteService, CursoService cursoService) {
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    // Página pública del portal
    @GetMapping("/portal")
    public String portalIndex() {
        return "portal/indexp"; // templates/portal/indexp.html
    }

    // Acceso - Login
    @GetMapping("/portal/acceso/login")
    public String loginPortal(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas. Por favor, intenta nuevamente.");
        }
        return "portal/acceso/loginportal"; // templates/portal/acceso/loginportal.html
    }

    @PostMapping("/portal/acceso/login")
    public String processLogin(@RequestParam String usuario, @RequestParam String password, Model model) {
        // Aquí iría la lógica de autenticación
        // Por ahora, redirigimos al panel del estudiante
        if (usuario != null && password != null && !usuario.isEmpty() && !password.isEmpty()) {
            return "redirect:/portal/estudiante";
        } else {
            model.addAttribute("error", "Por favor, completa todos los campos.");
            return "portal/acceso/loginportal";
        }
    }

    // Acceso - Recuperar contraseña
    @GetMapping("/portal/acceso/recuperar")
    public String recuperarPassword(@RequestParam(required = false) String success, 
                                     @RequestParam(required = false) String error, 
                                     Model model) {
        if (success != null) {
            model.addAttribute("success", "Se ha enviado un enlace de recuperación a tu correo electrónico.");
        }
        if (error != null) {
            model.addAttribute("error", "No se encontró una cuenta con ese correo electrónico.");
        }
        return "portal/acceso/recuperar"; // templates/portal/acceso/recuperar.html
    }

    @PostMapping("/portal/acceso/recuperar")
    public String processRecuperar(@RequestParam String email, Model model) {
        // Aquí iría la lógica de recuperación de contraseña
        // Por ahora, simulamos el envío
        if (email != null && !email.isEmpty() && email.contains("@")) {
            model.addAttribute("success", "Se ha enviado un enlace de recuperación a tu correo electrónico.");
        } else {
            model.addAttribute("error", "Por favor, ingresa un correo electrónico válido.");
        }
        return "portal/acceso/recuperar";
    }

    // Panel del Estudiante - Inicio
    @GetMapping("/portal/estudiante")
    public String portalEstudianteIndex(Model model) {
        model.addAttribute("nombreAlumno", "Alumno");
        model.addAttribute("totalCursos", cursoService.listar().size());
        model.addAttribute("horasSemana", 0);
        model.addAttribute("cuotasPendientes", 0);
        return "portal/portalestudiante/portalindex"; // templates/portal/portalestudiante/portalindex.html
    }

    // Panel del Estudiante - Cursos
    @GetMapping("/portal/estudiante/cursos")
    public String portalEstudianteCursos(Model model) {
        model.addAttribute("totalCursos", cursoService.listar().size());
        model.addAttribute("totalCreditos", 0);
        model.addAttribute("promedio", 0);
        model.addAttribute("horasTotales", 0);
        return "portal/portalestudiante/cursos"; // templates/portal/portalestudiante/cursos.html
    }

    // Panel del Estudiante - Horario
    @GetMapping("/portal/estudiante/horario")
    public String portalEstudianteHorario(Model model) {
        // Datos de ejemplo para horario
        model.addAttribute("semestre", "Semestre 3");
        model.addAttribute("totalHoras", 20);
        model.addAttribute("totalCursos", 4);
        model.addAttribute("totalCreditos", 14);
        return "portal/portalestudiante/horario"; // templates/portal/portalestudiante/horario.html
    }

    // Panel del Estudiante - Pagos
    @GetMapping("/portal/estudiante/pagos")
    public String portalEstudiantePagos(Model model) {
        // Datos de ejemplo para pagos
        model.addAttribute("montoTotal", "S/ 5,000.00");
        model.addAttribute("cuotasPagadas", 3);
        model.addAttribute("cuotasPendientes", 2);
        model.addAttribute("porcentajePagado", "60%");
        return "portal/portalestudiante/pagos"; // templates/portal/portalestudiante/pagos.html
    }

    // Panel del Estudiante - Error
    @GetMapping("/portal/estudiante/error")
    public String portalEstudianteError(@RequestParam(required = false) String mensaje, Model model) {
        if (mensaje != null) {
            model.addAttribute("mensaje", mensaje);
        }
        return "portal/portalestudiante/error"; // templates/portal/portalestudiante/error.html
    }
}
