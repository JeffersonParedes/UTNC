package Grupo_5.UTNC.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MatriculaCreateDTO {
    @NotNull
    private Integer idEstudiante;

    @NotNull
    private Integer idCurso;

    @Size(max = 20)
    private String cicloAcademico;

    @Size(max = 20)
    private String estado;

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getCicloAcademico() {
        return cicloAcademico;
    }

    public void setCicloAcademico(String cicloAcademico) {
        this.cicloAcademico = cicloAcademico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
