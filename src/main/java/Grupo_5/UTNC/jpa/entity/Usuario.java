package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "username", columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String username;

    @Column(name = "email", columnDefinition = "VARCHAR(150)", nullable = false, unique = true)
    private String email;

    @Column(name = "contraseña_hash", columnDefinition = "VARCHAR(255)", nullable = false)
    private String contrasenaHash;

    // Dejar que la BD aplique DEFAULT 'activo'
    @Column(name = "estado", columnDefinition = "VARCHAR(20)", insertable = false, updatable = false)
    private String estado;

    // Dejar que la BD aplique DEFAULT CURRENT_TIMESTAMP
    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_acceso", columnDefinition = "TIMESTAMP")
    private LocalDateTime ultimoAcceso;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }
}
