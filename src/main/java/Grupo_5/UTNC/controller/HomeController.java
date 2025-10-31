package Grupo_5.UTNC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros/nosotros"; // templates/nosotros/nosotros.html
    }

    @GetMapping("/carreras")
    public String carreras() {
        return "carreras/indexcarreras"; // templates/carreras/indexcarreras.html
    }

    @GetMapping("/admision")
    public String admision() {
        return "admision/admision"; // templates/admision/admision.html
    }

    @GetMapping("/campus")
    public String campus() {
        return "institucional/institucional"; // templates/institucional/institucional.html
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto/contacto"; // templates/contacto/contacto.html
    }

    @GetMapping("/servicios")
    public String servicios() {
        return "servicios/servicios"; // templates/servicios/servicios.html
    }

    @GetMapping("/admision/inscripcion")
    public String admisionInscripcion() {
        return "admision/inscripcion";
    }
}
