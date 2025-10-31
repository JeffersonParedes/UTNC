package Grupo_5.UTNC.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactoDTO {

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    @NotEmpty(message = "La carrera no puede estar vacía")
    private String carrera;

    // Aunque el JS valida la edad, la omitimos aquí para simplificar,
    // ya que no parece un campo estándar para un formulario de contacto general.

    @NotEmpty(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotEmpty(message = "El asunto no puede estar vacío")
    private String subject;

    @NotEmpty(message = "El mensaje no puede estar vacío")
    @Size(min = 10, message = "El mensaje debe tener al menos 10 caracteres")
    private String message;
}
