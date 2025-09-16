package org.binaryminds.kinalnotes.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Docentes")
@Data
public class DocenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(length = 40,  nullable = false)
    private String nombre;
    @Column(name="codigo_curso", precision = 3, unique = true, nullable = false)
    private Integer codigoCurso;
}
