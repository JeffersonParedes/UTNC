package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(name = "nombres", columnDefinition = "VARCHAR(100)", nullable = false)
    private String nombres;

    @Column(name = "apellidos", columnDefinition = "VARCHAR(100)", nullable = false)
    private String apellidos;

    // <-- CORRECCIÓN IMPORTANTE: forzar CHAR(8)
    @Column(name = "dni", columnDefinition = "CHAR(8)", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "fecha_nacimiento", columnDefinition = "DATE", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "genero", columnDefinition = "VARCHAR(20)")
    private String genero;

    @Column(name = "telefono", columnDefinition = "VARCHAR(15)")
    private String telefono;

    @Column(name = "colegio", columnDefinition = "VARCHAR(150)")
    private String colegio;

    // mantener el nombre exacto de la columna con ñ en la BD
    @Column(name = "año_egreso", columnDefinition = "VARCHAR(20)")
    private String anioEgreso;

    @Column(name = "semestre_actual", columnDefinition = "INT")
    private Integer semestreActual = 1; // puedes inicializar en Java o dejar que la BD aplique DEFAULT

    @Column(name = "discapacidad", columnDefinition = "BOOLEAN")
    private Boolean discapacidad = false;

    @Column(name = "discapacidad_detalle", columnDefinition = "VARCHAR(255)")
    private String discapacidadDetalle;

    @Column(name = "contacto_emergencia", columnDefinition = "VARCHAR(100)")
    private String contactoEmergencia;

    @Column(name = "telefono_emergencia", columnDefinition = "VARCHAR(15)")
    private String telefonoEmergencia;

    @Column(name = "acepta_terminos", columnDefinition = "BOOLEAN")
    private Boolean aceptaTerminos = false;

    @Column(name = "estado_academico", columnDefinition = "VARCHAR(20)")
    private String estadoAcademico = "activo";

    // Dejar que la BD ponga el timestamp por defecto: evitar que Hibernate incluya la columna en INSERT
    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // getters y setters...
    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getColegio() { return colegio; }
    public void setColegio(String colegio) { this.colegio = colegio; }

    public String getAnioEgreso() { return anioEgreso; }
    public void setAnioEgreso(String anioEgreso) { this.anioEgreso = anioEgreso; }

    public Integer getSemestreActual() { return semestreActual; }
    public void setSemestreActual(Integer semestreActual) { this.semestreActual = semestreActual; }

    public Boolean getDiscapacidad() { return discapacidad; }
    public void setDiscapacidad(Boolean discapacidad) { this.discapacidad = discapacidad; }

    public String getDiscapacidadDetalle() { return discapacidadDetalle; }
    public void setDiscapacidadDetalle(String discapacidadDetalle) { this.discapacidadDetalle = discapacidadDetalle; }

    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }

    public String getTelefonoEmergencia() { return telefonoEmergencia; }
    public void setTelefonoEmergencia(String telefonoEmergencia) { this.telefonoEmergencia = telefonoEmergencia; }

    public Boolean getAceptaTerminos() { return aceptaTerminos; }
    public void setAceptaTerminos(Boolean aceptaTerminos) { this.aceptaTerminos = aceptaTerminos; }

    public String getEstadoAcademico() { return estadoAcademico; }
    public void setEstadoAcademico(String estadoAcademico) { this.estadoAcademico = estadoAcademico; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
