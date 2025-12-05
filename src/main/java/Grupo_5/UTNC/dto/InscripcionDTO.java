package Grupo_5.UTNC.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class InscripcionDTO {

    @NotNull
    @Valid
    private UsuarioCreateDTO usuario;

    @NotNull
    @Valid
    private EstudianteCreateDTO estudiante;

    @NotNull
    private String careerName;

    public UsuarioCreateDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioCreateDTO usuario) {
        this.usuario = usuario;
    }

    public EstudianteCreateDTO getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteCreateDTO estudiante) {
        this.estudiante = estudiante;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }
}
