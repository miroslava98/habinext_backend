package org.example.backend_habinext.properties.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend_habinext.auth.entities.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "propiedades")
public class Propiedad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private TipoPropiedad tipo;

    @Column(name = "descripcion_corta", length = 255)
    private String descripcionCorta;

    @Column(name = "descripcion_larga", length = 2000)
    private String descripcionLarga;

    @Column(name = "ubicacion", length = 255)
    private String ubicacion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "habitaciones")
    private Integer habitaciones;

    @Column(name = "banos")
    private Integer banos;

    @Column(name = "metros_cuadrados")
    private Double metrosCuadrados;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "antiguedad")
    private Integer antiguedad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50)
    private EstadoPropiedad estado;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "balcon")
    private Boolean balcon;

    @Column(name = "calefaccion")
    private Boolean calefaccion;

    @ElementCollection
    @Column(name = "fotos")
    private List<String> fotos = new ArrayList<>();


}
