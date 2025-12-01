package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(name = "dia_semana", columnDefinition = "VARCHAR(20)")
    private String diaSemana;

    @Column(name = "hora_inicio", columnDefinition = "TIME", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", columnDefinition = "TIME", nullable = false)
    private LocalTime horaFin;

    @Column(name = "ciclo_academico", columnDefinition = "VARCHAR(20)", nullable = false)
    private String cicloAcademico;

    // Dejar que la BD aplique DEFAULT 'activo'
    @Column(name = "estado", columnDefinition = "VARCHAR(20)", insertable = false, updatable = false)
    private String estado;

    public Integer getIdHorario() { return idHorario; }
    public void setIdHorario(Integer idHorario) { this.idHorario = idHorario; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getCicloAcademico() { return cicloAcademico; }
    public void setCicloAcademico(String cicloAcademico) { this.cicloAcademico = cicloAcademico; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

