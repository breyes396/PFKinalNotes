package org.binaryminds.kinalnotes.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "Notas")
@Data
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(precision = 3, nullable = false)
    private Integer calificacion;
    @Column(name="codigo_curso", nullable = false)
    private Long codigoCurso;
    @Column(name="codigo_estudiante", nullable = false)
    private Long codigoEstudiante;
    @Column(name="codigo_docente", nullable = false)
    private Long codigoDocente;

}
