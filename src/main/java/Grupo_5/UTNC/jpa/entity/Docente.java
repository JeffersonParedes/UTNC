package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "docentes")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Integer idDocente;

    @Column(name = "nombres", columnDefinition = "VARCHAR(100)", nullable = false)
    private String nombres;

    @Column(name = "apellidos", columnDefinition = "VARCHAR(100)", nullable = false)
    private String apellidos;

    @Column(name = "email", columnDefinition = "VARCHAR(150)", nullable = false, unique = true)
    private String email;

    @Column(name = "telefono", columnDefinition = "VARCHAR(15)")
    private String telefono;

    @Column(name = "especialidad", columnDefinition = "VARCHAR(150)")
    private String especialidad;

    // Dejar que la BD aplique DEFAULT 'activo' si existe en el esquema
    @Column(name = "estado", columnDefinition = "VARCHAR(20)", insertable = false, updatable = false)
    private String estado;

    // Dejar que la BD aplique DEFAULT CURRENT_TIMESTAMP
    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    public Integer getIdDocente() { return idDocente; }
    public void setIdDocente(Integer idDocente) { this.idDocente = idDocente; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
