package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Horario;
import Grupo_5.UTNC.jpa.repository.HorarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImpl implements HorarioService {
    private final HorarioRepository horarioRepository;

    public HorarioServiceImpl(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Override
    public List<Horario> listar() {
        return horarioRepository.findAll();
    }

    @Override
    public Optional<Horario> obtener(Integer id) {
        return horarioRepository.findById(id);
    }

    @Override
    public Horario crear(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public List<Horario> listarPorCursoYCiclo(Integer idCurso, String ciclo) {
        return horarioRepository.findByCurso_IdCursoAndCicloAcademico(idCurso, ciclo);
    }
}
