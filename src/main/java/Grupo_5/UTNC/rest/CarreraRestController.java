package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.jpa.entity.Carrera;
import Grupo_5.UTNC.service.CarreraService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carreras")
public class CarreraRestController {
    private final CarreraService carreraService;

    public CarreraRestController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public List<Carrera> listar() {
        return carreraService.listar();
    }
}
