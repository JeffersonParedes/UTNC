package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Carrera;
import Grupo_5.UTNC.jpa.repository.CarreraRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraServiceImpl implements CarreraService {
    private final CarreraRepository carreraRepository;

    public CarreraServiceImpl(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public List<Carrera> listar() {
        return carreraRepository.findAll();
    }

    @Override
    public Optional<Carrera> obtener(Integer id) {
        return carreraRepository.findById(id);
    }

    @Override
    public Carrera crear(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    @Override
    public Carrera buscarPorNombre(String nombre) {
        return carreraRepository.findByNombreCarreraIgnoreCase(nombre).orElse(null);
    }
}
