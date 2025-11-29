package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Matricula;
import Grupo_5.UTNC.jpa.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaServiceImpl implements MatriculaService {
    private final MatriculaRepository matriculaRepository;

    public MatriculaServiceImpl(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    @Override
    public List<Matricula> listar() {
        return matriculaRepository.findAll();
    }

    @Override
    public Optional<Matricula> obtener(Integer id) {
        return matriculaRepository.findById(id);
    }

    @Override
    public Matricula crear(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    @Override
    public List<Matricula> listarPorEstudianteCiclo(Integer idEstudiante, String ciclo) {
        return matriculaRepository.findByEstudiante_IdEstudianteAndCicloAcademico(idEstudiante, ciclo);
    }

    @Override
    public List<Matricula> listarPorCurso(Integer idCurso) {
        return matriculaRepository.findByCurso_IdCurso(idCurso);
    }
}
