package org.example.backend_habinext.auth.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    @Email
    private String correo;
    private String rol;

    @Size(min = 5)
    private String contrasenya;
}
