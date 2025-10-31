package Grupo_5.UTNC.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contactos")
@Data
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "carrera_interes", nullable = false, length = 50)
    private String carrera;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 100)
    private String asunto;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @CreationTimestamp
    @Column(name = "fecha_envio", updatable = false)
    private LocalDateTime fechaEnvio;
}
