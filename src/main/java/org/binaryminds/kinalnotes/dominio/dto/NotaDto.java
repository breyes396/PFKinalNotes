package org.binaryminds.kinalnotes.dominio.dto;

import java.time.LocalDate;
import java.util.List;

public record NotaDto(
        Long codigo,
        LocalDate fecha,
        double calificacion,
        Long codigo_estudiante,
        Long codigo_curso,
        Long codigo_docente
) {
}
