package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Docente;
import java.util.List;
import java.util.Optional;

public interface DocenteService {
    List<Docente> listar();
    Optional<Docente> obtener(Integer id);
    Docente crear(Docente docente);
}
