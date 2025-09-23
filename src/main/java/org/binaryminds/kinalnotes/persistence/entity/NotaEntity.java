package org.binaryminds.kinalnotes.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Notas")
@Data
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(length = 40,  nullable = false)
    private LocalDate fecha;
    @Column(length = 40, nullable = false)
    private double calificacion;
    @Column(name="codigo_curso", precision = 3, unique = true, nullable = false)
    private Long codigoCurso;
    @Column(name="codigo_estudiante", precision = 3, unique = true, nullable = false)
    private Long codigo_estudiante;
    @Column(name="codigo_docente", precision = 3, unique = true, nullable = false)
    private Long codigo_docente;


}
