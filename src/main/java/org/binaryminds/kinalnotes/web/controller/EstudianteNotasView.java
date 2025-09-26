package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.binaryminds.kinalnotes.dominio.service.NotaService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@ViewScoped
@Getter
@Setter
public class EstudianteNotasView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final AuthSession authSession;
    private final NotaService notaService;
    private final CursoService cursoService;

    public static class NotaRow implements Serializable {
        private Long notaCodigo;
        private Long cursoCodigo;
        private String cursoNombre;
        private Integer calificacion;
        public NotaRow(Long notaCodigo, Long cursoCodigo, String cursoNombre, Integer calificacion){
            this.notaCodigo = notaCodigo;
            this.cursoCodigo = cursoCodigo;
            this.cursoNombre = cursoNombre;
            this.calificacion = calificacion;
        }
    }

    private java.util.List<NotaRow> notas;

    public EstudianteNotasView(AuthSession authSession, NotaService notaService, CursoService cursoService) {
        this.authSession = authSession;
        this.notaService = notaService;
        this.cursoService = cursoService;
    }

    @PostConstruct
    public void init(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(!authSession.isLogged() || !authSession.isStudent()){
            try { ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/pages/login.xhtml"); } catch (IOException ignored) {}
            return;
        }
        cargarNotas();
    }

    public void cargarNotas(){
        Long estId = authSession.getEstudianteCodigo();
        if(estId == null){
            notas = java.util.List.of();
            return;
        }
        List<NotaDto> lista = notaService.obtenerNotasPorEstudiante(estId);
        Map<Long,String> nombresCursos = new HashMap<>();
        for(CursoDto c : cursoService.obtenerTodo()){
            nombresCursos.put(c.codigo(), c.name());
        }
        notas = lista.stream()
                .map(n -> new NotaRow(n.codigo(), n.codigo_curso(), nombresCursos.getOrDefault(n.codigo_curso(), "(Sin nombre)"), n.calificacion()))
                .collect(Collectors.toList());
        log.debug("Notas cargadas: {}", notas.size());
    }

    public String styleNota(Integer nota){
        if(nota == null) return "color:#9ca3af !important;font-weight:bold;";
        if(nota < 60) return "color:#f87171 !important;font-weight:bold;";
        if(nota < 80) return "color:#fbbf24 !important;font-weight:bold;";
        if(nota < 95) return "color:#34d399 !important;font-weight:bold;";
        return "color:#10b981 !important;font-weight:bold;";
    }
}

