package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService {
    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public List<Estudiante> listar() {
        return estudianteRepository.findAll();
    }

    @Override
    public Optional<Estudiante> obtener(Integer id) {
        return estudianteRepository.findById(id);
    }

    @Override
    @Transactional
    public Estudiante crear(Estudiante estudiante) {
        estudiante.setFechaRegistro(LocalDateTime.now());
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Optional<Estudiante> buscarPorDni(String dni) {
        return estudianteRepository.findByDni(dni);
    }

    @Override
    public List<Estudiante> listarPorCarreraSemestre(Integer idCarrera, Integer semestre) {
        return estudianteRepository.findByCarrera_IdCarreraAndSemestreActual(idCarrera, semestre);
    }
}
