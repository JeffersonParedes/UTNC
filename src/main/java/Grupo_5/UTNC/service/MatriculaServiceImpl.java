package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Curso;
import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.entity.Matricula;
import Grupo_5.UTNC.jpa.repository.CursoRepository;
import Grupo_5.UTNC.jpa.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaServiceImpl implements MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final CursoRepository cursoRepository;

    public MatriculaServiceImpl(MatriculaRepository matriculaRepository, CursoRepository cursoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.cursoRepository = cursoRepository;
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

    @Override
    @Transactional
    public void matricularEstudianteInicial(Estudiante estudiante, String cicloAcademico) {
        Integer idCarrera = estudiante.getCarrera().getIdCarrera();
        List<Curso> cursosSemestre1 = cursoRepository.findByCarrera_IdCarreraAndSemestre(idCarrera, 1);
        for (Curso curso : cursosSemestre1) {
            Matricula m = new Matricula();
            m.setEstudiante(estudiante);
            m.setCurso(curso);
            m.setCicloAcademico(cicloAcademico);
            crear(m);
        }
    }
}
