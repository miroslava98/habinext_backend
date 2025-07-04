package org.example.backend_habinext.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String nombre;
    private String correo;
    private String rol;
    private String contrasenya;
}
