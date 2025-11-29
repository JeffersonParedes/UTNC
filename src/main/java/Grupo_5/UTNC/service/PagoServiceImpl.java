package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Pago;
import Grupo_5.UTNC.jpa.repository.PagoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {
    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> obtener(Integer id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago crear(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public List<Pago> listarPorEstudianteYCiclo(Integer idEstudiante, String ciclo) {
        return pagoRepository.findByEstudiante_IdEstudianteAndCicloAcademico(idEstudiante, ciclo);
    }
}
