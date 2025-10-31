package Grupo_5.UTNC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CarreraController {

    @GetMapping("/carreras/{slug}")
    public String carreraDetalle(@PathVariable String slug) {
        return "carreras/" + slug;
    }
}


