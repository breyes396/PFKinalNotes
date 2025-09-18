package org.binaryminds.kinalnotes.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Estudiantes")
@Data
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(length = 40, nullable = false)
    private String nombre;
    @Column(length = 40, nullable = false)
    private String apellido;
    @ManyToMany
    @JoinTable(
            name = "estudiante_curso", // Nombre de la tabla intermedia que JPA generará
            joinColumns = @JoinColumn(name = "id_estudiante"),
            inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<CursoEntity> cursos;
    @OneToOne
    @JoinColumn(name = "codigo_usuario" ,referencedColumnName = "codigo_usuario")
    private UsuarioEntity usuario;
}

