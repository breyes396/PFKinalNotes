package org.binaryminds.kinalnotes.dominio.dto;

import java.util.List;

public record EstudianteDto(
        Long codigo,
        String name,
        String lastname,
        List<Long> courses,
        Long codigo_usuario

) {
}
