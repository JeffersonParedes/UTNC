package Grupo_5.UTNC.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import Grupo_5.UTNC.validator.ValidDNI;
import java.time.LocalDate;

public class EstudianteCreateDTO {
    @NotNull
    private Integer idUsuario;

    @NotNull
    private Integer idCarrera;

    @NotBlank
    @Size(max = 100)
    private String nombres;

    @NotBlank
    @Size(max = 100)
    private String apellidos;

    @NotBlank
    @ValidDNI
    private String dni;

    @NotNull
    private LocalDate fechaNacimiento;

    @Size(max = 20)
    private String genero;

    @Size(max = 15)
    private String telefono;

    @Size(max = 150)
    private String colegio;

    @Size(max = 20)
    private String anioEgreso;

    private Integer semestreActual;

    private Boolean discapacidad;

    @Size(max = 255)
    private String discapacidadDetalle;

    @Size(max = 100)
    private String contactoEmergencia;

    @Size(max = 15)
    private String telefonoEmergencia;

    private Boolean aceptaTerminos;

    @Size(max = 20)
    private String estadoAcademico;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(String anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public Integer getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(Integer semestreActual) {
        this.semestreActual = semestreActual;
    }

    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getDiscapacidadDetalle() {
        return discapacidadDetalle;
    }

    public void setDiscapacidadDetalle(String discapacidadDetalle) {
        this.discapacidadDetalle = discapacidadDetalle;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public Boolean getAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(Boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public String getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(String estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }
}
