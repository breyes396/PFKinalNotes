package org.binaryminds.kinalnotes.dominio.dto;

import java.util.List;

public record CursoDto(
        Long codigo,
        String name,
        String degree,
        List<Long> students
) {
}
