package org.binaryminds.kinalnotes.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuarios")
@Data
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(length = 150,  nullable = false)
    private String correo;
    @Column(length = 40, unique = true,nullable = false)
    private String nombre;
    @Column( length = 40, nullable = false)
    private String contrasena;
    @Column( length = 40, nullable = false)
    private String rol;

}
