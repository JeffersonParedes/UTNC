package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Pago;
import java.util.List;
import java.util.Optional;

public interface PagoService {
    List<Pago> listar();
    Optional<Pago> obtener(Integer id);
    Pago crear(Pago pago);
    List<Pago> listarPorEstudianteYCiclo(Integer idEstudiante, String ciclo);
}
