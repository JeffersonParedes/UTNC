package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.dto.EstudianteCreateDTO;
import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    List<Estudiante> listar();
    Optional<Estudiante> obtener(Integer id);
    Estudiante crear(Estudiante estudiante);
    Optional<Estudiante> buscarPorDni(String dni);
    List<Estudiante> listarPorCarreraSemestre(Integer idCarrera, Integer semestre);
    Estudiante crearDesdeDTO(EstudianteCreateDTO dto);
}
