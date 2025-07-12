package org.example.backend_habinext.auth.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "propiedades")
public class Propiedad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



}
