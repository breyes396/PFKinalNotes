package org.binaryminds.kinalnotes.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Cursos")
@Data
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(length = 40, nullable = false)
    private String nombre;
    @Column(length = 40, nullable = false)
    private String grado;
    @ManyToMany(mappedBy = "cursos")
    private List<EstudianteEntity> estudiantes;
}
