package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer idCurso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @Column(name = "codigo_curso", columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String codigoCurso;

    @Column(name = "nombre_curso", columnDefinition = "VARCHAR(150)", nullable = false)
    private String nombreCurso;

    @Column(name = "semestre", columnDefinition = "INT", nullable = false)
    private Integer semestre;

    @Column(name = "creditos", columnDefinition = "INT", nullable = false)
    private Integer creditos;

    @Column(name = "horas_semanales", columnDefinition = "INT", nullable = false)
    private Integer horasSemanales;

    @Column(name = "aula", columnDefinition = "VARCHAR(50)")
    private String aula;

    // Dejar que la BD aplique DEFAULT 'activo' si existe en el esquema
    @Column(name = "estado", columnDefinition = "VARCHAR(20)", insertable = false, updatable = false)
    private String estado;

    // Dejar que la BD aplique DEFAULT CURRENT_TIMESTAMP
    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }

    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }

    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }

    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }

    public Integer getHorasSemanales() { return horasSemanales; }
    public void setHorasSemanales(Integer horasSemanales) { this.horasSemanales = horasSemanales; }

    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
