package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_estudiante", "id_curso"})
})
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer idMatricula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(name = "ciclo_academico", columnDefinition = "VARCHAR(20)", nullable = false)
    private String cicloAcademico;

    @Column(name = "nota_final", columnDefinition = "DECIMAL(4,2)")
    private BigDecimal notaFinal;

    // Dejar que la BD aplique DEFAULT 'matriculado'
    @Column(name = "estado", columnDefinition = "VARCHAR(20)", insertable = false, updatable = false)
    private String estado;

    // Dejar que la BD aplique DEFAULT CURRENT_TIMESTAMP
    @Column(name = "fecha_matricula", columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaMatricula;

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getCicloAcademico() {
        return cicloAcademico;
    }

    public void setCicloAcademico(String cicloAcademico) {
        this.cicloAcademico = cicloAcademico;
    }

    public BigDecimal getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(BigDecimal notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getEstado() {
        return estado;
    }

    // No es necesario setear estado si la BD lo controla, pero dejo el setter por si lo necesitas
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaMatricula() {
        return fechaMatricula;
    }

    // Si la BD controla el valor, evita setearlo desde la app; el setter queda disponible si lo necesitas
    public void setFechaMatricula(LocalDateTime fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }
}
