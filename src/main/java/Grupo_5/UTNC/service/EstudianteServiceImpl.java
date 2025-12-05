package Grupo_5.UTNC.service;

import Grupo_5.UTNC.dto.EstudianteCreateDTO;
import Grupo_5.UTNC.jpa.entity.Carrera;
import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.jpa.repository.CarreraRepository;
import Grupo_5.UTNC.jpa.repository.EstudianteRepository;
import Grupo_5.UTNC.jpa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService {
    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, UsuarioRepository usuarioRepository, CarreraRepository carreraRepository) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.carreraRepository = carreraRepository;
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

    @Override
    @Transactional
    public Estudiante crearDesdeDTO(EstudianteCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow();
        Carrera carrera = carreraRepository.findById(dto.getIdCarrera()).orElseThrow();

        Estudiante e = new Estudiante();
        e.setUsuario(usuario);
        e.setCarrera(carrera);
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setDni(dto.getDni());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setGenero(dto.getGenero());
        e.setTelefono(dto.getTelefono());
        e.setColegio(dto.getColegio());
        e.setAnioEgreso(dto.getAnioEgreso());
        e.setSemestreActual(dto.getSemestreActual());
        e.setDiscapacidad(dto.getDiscapacidad());
        e.setDiscapacidadDetalle(dto.getDiscapacidadDetalle());
        e.setContactoEmergencia(dto.getContactoEmergencia());
        e.setTelefonoEmergencia(dto.getTelefonoEmergencia());
        e.setAceptaTerminos(dto.getAceptaTerminos());
        e.setEstadoAcademico(dto.getEstadoAcademico());

        return crear(e);
    }
}
