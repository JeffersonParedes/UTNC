package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.PagoCreateDTO;
import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.entity.Pago;
import Grupo_5.UTNC.service.EstudianteService;
import Grupo_5.UTNC.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoRestController {
    private final PagoService pagoService;
    private final EstudianteService estudianteService;

    public PagoRestController(PagoService pagoService, EstudianteService estudianteService) {
        this.pagoService = pagoService;
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<Pago> listar() {
        return pagoService.listar();
    }

    @PostMapping
    public ResponseEntity<Pago> crear(@Valid @RequestBody PagoCreateDTO dto) {
        Estudiante estudiante = estudianteService.obtener(dto.getIdEstudiante()).orElse(null);
        if (estudiante == null) {
            return ResponseEntity.badRequest().build();
        }
        Pago p = new Pago();
        p.setEstudiante(estudiante);
        p.setCicloAcademico(dto.getCicloAcademico());
        p.setNumeroCuota(dto.getNumeroCuota());
        p.setConcepto(dto.getConcepto());
        p.setMonto(dto.getMonto());
        p.setFechaVencimiento(dto.getFechaVencimiento());
        p.setEstado(dto.getEstado());
        Pago creado = pagoService.crear(p);
        return ResponseEntity.created(URI.create("/api/pagos/" + creado.getIdPago())).body(creado);
    }
}
