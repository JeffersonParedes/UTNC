package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Carrera;
import java.util.List;
import java.util.Optional;

public interface CarreraService {
    List<Carrera> listar();
    Optional<Carrera> obtener(Integer id);
    Carrera crear(Carrera carrera);
    Carrera buscarPorNombre(String nombre);
}
