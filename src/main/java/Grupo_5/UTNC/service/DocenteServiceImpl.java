package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Docente;
import Grupo_5.UTNC.jpa.repository.DocenteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocenteServiceImpl implements DocenteService {
    private final DocenteRepository docenteRepository;

    public DocenteServiceImpl(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    @Override
    public List<Docente> listar() {
        return docenteRepository.findAll();
    }

    @Override
    public Optional<Docente> obtener(Integer id) {
        return docenteRepository.findById(id);
    }

    @Override
    public Docente crear(Docente docente) {
        return docenteRepository.save(docente);
    }
}
