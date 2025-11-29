package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Curso;
import Grupo_5.UTNC.jpa.repository.CursoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {
    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> obtener(Integer id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso crear(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> listarPorCarreraSemestre(Integer idCarrera, Integer semestre) {
        return cursoRepository.findByCarrera_IdCarreraAndSemestre(idCarrera, semestre);
    }
}
