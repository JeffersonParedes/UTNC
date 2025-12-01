package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carreras")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera")
    private Integer idCarrera;

    @Column(name = "codigo_carrera", columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String codigoCarrera;

    @Column(name = "nombre_carrera", columnDefinition = "VARCHAR(150)", nullable = false)
    private String nombreCarrera;

    @Column(name = "duracion_semestres", columnDefinition = "INT", nullable = false)
    private Integer duracionSemestres;

    @Column(name = "titulo_otorgado", columnDefinition = "VARCHAR(150)", nullable = false)
    private String tituloOtorgado;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado", columnDefinition = "VARCHAR(20)")
    private String estado = "activo"; // espejo del DEFAULT 'activo' en BD

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion = LocalDateTime.now(); // espejo del DEFAULT CURRENT_TIMESTAMP en BD

    public Integer getIdCarrera() { return idCarrera; }
    public void setIdCarrera(Integer idCarrera) { this.idCarrera = idCarrera; }

    public String getCodigoCarrera() { return codigoCarrera; }
    public void setCodigoCarrera(String codigoCarrera) { this.codigoCarrera = codigoCarrera; }

    public String getNombreCarrera() { return nombreCarrera; }
    public void setNombreCarrera(String nombreCarrera) { this.nombreCarrera = nombreCarrera; }

    public Integer getDuracionSemestres() { return duracionSemestres; }
    public void setDuracionSemestres(Integer duracionSemestres) { this.duracionSemestres = duracionSemestres; }

    public String getTituloOtorgado() { return tituloOtorgado; }
    public void setTituloOtorgado(String tituloOtorgado) { this.tituloOtorgado = tituloOtorgado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}

