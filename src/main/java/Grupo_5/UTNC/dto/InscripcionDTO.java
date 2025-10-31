package Grupo_5.UTNC.dto;

import Grupo_5.UTNC.validator.NoBadWords;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar los datos del formulario de inscripción.
 * La anotación @Data de Lombok genera automáticamente:
 * - Getters para todos los campos.
 * - Setters para todos los campos.
 * - Un constructor que requiere todos los campos.
 * - El método toString().
 * - Los métodos equals() y hashCode().
 */
@Data
public class InscripcionDTO {

    // Step 1: Personal Information
    @NoBadWords
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String firstName;

    @NotEmpty(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotEmpty(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotEmpty(message = "La fecha de nacimiento no puede estar vacía")
    private String birthDate;

    @NotEmpty(message = "Debe seleccionar un género")
    private String gender;

    @NotEmpty(message = "El teléfono no puede estar vacío")
    private String phone;

    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;

    // Step 2: Academic Information
    @NotEmpty(message = "El nombre del colegio no puede estar vacío")
    private String schoolName;

    @NotEmpty(message = "Debe seleccionar un año de egreso")
    private String graduationYear;

    @NotEmpty(message = "Debe seleccionar una carrera")
    private String career;

    // Step 3: Additional Information
    private boolean hasDisability;
    private String emergencyContact;
    private String emergencyPhone;

    // Step 4: Review and Submit
    @NotNull(message = "Debe aceptar los términos y condiciones")
    private boolean acceptTerms;

}
