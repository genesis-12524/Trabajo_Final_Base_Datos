package com.fia.base.controller;

import com.fia.base.model.entity.*;

import com.fia.base.model.DTO.*;
import com.fia.base.model.dao.*;
import com.fia.base.model.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fia.base.model.DTO.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/admin")
public class AreaCurricularController {

    @Autowired
    private IAreaCurri areaCurriRepository;
    
    @Autowired
    private IAsignatura asignaturaRepository;
    
    @Autowired
    private IDocente docenteRepository;
   
    @Autowired
    private IDocenteAsignatura docenteAsignaturaRepository;
    private static final Logger logger = LoggerFactory.getLogger(AreaCurricularController.class);
    @GetMapping("/asignar-responsables")
    public String mostrarAsignacionResponsables(Model model) {
        List<AreaCurri> areasCurriculares = areaCurriRepository.findAll();
        model.addAttribute("areasCurriculares", areasCurriculares);
        return "Designar";
    }

    @GetMapping("/asignaturas-por-area")
    public String mostrarAsignaturasPorArea(@RequestParam("areaId") Long areaId, Model model) {
        // Obtener el área curricular
        AreaCurri area = areaCurriRepository.findById(areaId).orElse(null);
        if (area == null) {
            return "redirect:/admin/asignar-responsables";
        }

        // Obtener todas las asignaturas del área
        List<Asignatura> asignaturas = asignaturaRepository.findByAreaCurri(area);
        
        // Lista para almacenar los datos que enviaremos a la vista
        List<AsignaturaConDocentesDTO> asignaturasConDocentes = new ArrayList<>();

        // Para cada asignatura, obtener sus docentes
        for (Asignatura asignatura : asignaturas) {
            List<DocenteAsignatura> relaciones = docenteAsignaturaRepository.findByAsignatura(asignatura);
            List<Docente> docentes = relaciones.stream()
                .map(DocenteAsignatura::getDocente)
                .collect(Collectors.toList());
            
            asignaturasConDocentes.add(new AsignaturaConDocentesDTO(
                asignatura.getId(), 
                asignatura.getNombre(), 
                docentes
            ));
        }

        // Agregar datos al modelo
        model.addAttribute("area", area);
        model.addAttribute("asignaturasConDocentes", asignaturasConDocentes);
       
        
        return "DesignarProfe";
    }
    
    @PostMapping("/asignar-docente")
    public String asignarDocente(
            @RequestParam("areaId") Long areaId,
            @RequestParam("docenteId") Long docenteId) {
        
        // Solo actualizar el estado del docente
        Docente docente = docenteRepository.findById(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        
        docente.setEstado_encargado("ENCARGADO");
        docenteRepository.save(docente);
        
        // Redirigir manteniendo el contexto del área
        return "redirect:/admin/asignaturas-por-area?areaId=" + areaId;
    }
    
   
    
    
    
    
    
    @GetMapping("/docentes-encargados")
    public void generarReporteDocentesEncargados(HttpServletResponse response) throws IOException {
        // Configurar la respuesta para descarga de PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"docentes_encargados.pdf\"");
        
        // Crear el documento PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            // Manejar el contentStream manualmente en lugar de usar try-with-resources
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                // Configuración inicial
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float yPosition = yStart;
                float leading = 14;
                
                // Título del reporte
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("REPORTE DE DOCENTES ENCARGADOS POR ÁREA CURRICULAR");
                contentStream.endText();
                yPosition -= leading * 2;
                
                // Obtener todas las áreas curriculares
                List<AreaCurri> areas = areaCurriRepository.findAll();
                
                // Fuente para contenido normal
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                
                for (AreaCurri area : areas) {
                    // Verificar si necesitamos nueva página
                    if (yPosition < margin) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                        yPosition = yStart;
                    }
                    
                    // Mostrar área curricular
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Área Curricular: " + area.getNombre());
                    contentStream.endText();
                    yPosition -= leading;
                    
                    // Obtener docentes encargados para esta área
                    List<Docente> docentesEncargados = docenteRepository.findAll()
                            .stream()
                            .filter(d -> "ENCARGADO".equals(d.getEstado_encargado()))
                            .filter(d -> tieneAsignaturasEnArea(d, area))
                            .collect(Collectors.toList());
                    
                    if (docentesEncargados.isEmpty()) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        contentStream.showText("No hay docentes encargados en esta área.");
                        contentStream.endText();
                        yPosition -= leading;
                    } else {
                        for (Docente docente : docentesEncargados) {
                            // Verificar espacio nuevamente antes de agregar contenido
                            if (yPosition < margin) {
                                contentStream.close();
                                page = new PDPage();
                                document.addPage(page);
                                contentStream = new PDPageContentStream(document, page);
                                contentStream.setFont(PDType1Font.HELVETICA, 10);
                                yPosition = yStart;
                            }
                            
                            // Mostrar información del docente
                            contentStream.beginText();
                            contentStream.newLineAtOffset(margin + 20, yPosition);
                            contentStream.showText("Docente: " + docente.getApe() + ", " + docente.getNom() + 
                                                 " - Grado: " + docente.getGradoA());
                            contentStream.endText();
                            yPosition -= leading;
                            
                            // Obtener asignaturas del docente en esta área
                            List<Asignatura> asignaturas = docenteAsignaturaRepository.findByDocente(docente)
                                    .stream()
                                    .map(DocenteAsignatura::getAsignatura)
                                    .filter(a -> a.getAreaCurri().getId().equals(area.getId()))
                                    .collect(Collectors.toList());
                            
                            if (!asignaturas.isEmpty()) {
                                // Verificar espacio antes de agregar asignaturas
                                if (yPosition < margin + 60) {
                                    contentStream.close();
                                    page = new PDPage();
                                    document.addPage(page);
                                    contentStream = new PDPageContentStream(document, page);
                                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                                    yPosition = yStart;
                                }
                                
                                contentStream.beginText();
                                contentStream.newLineAtOffset(margin + 40, yPosition);
                                contentStream.showText("Asignaturas:");
                                contentStream.endText();
                                yPosition -= leading;
                                
                                for (Asignatura asignatura : asignaturas) {
                                    // Verificar espacio para cada asignatura
                                    if (yPosition < margin) {
                                        contentStream.close();
                                        page = new PDPage();
                                        document.addPage(page);
                                        contentStream = new PDPageContentStream(document, page);
                                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                                        yPosition = yStart;
                                    }
                                    
                                    contentStream.beginText();
                                    contentStream.newLineAtOffset(margin + 60, yPosition);
                                    contentStream.showText("- " + asignatura.getNombre());
                                    contentStream.endText();
                                    yPosition -= leading;
                                }
                            }
                            
                            // Espacio entre docentes
                            yPosition -= leading / 2;
                        }
                    }
                    
                    // Espacio entre áreas
                    yPosition -= leading;
                }
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }
            
            // Guardar el documento en la respuesta
            document.save(response.getOutputStream());
        }
    }

    private boolean tieneAsignaturasEnArea(Docente docente, AreaCurri area) {
        return docenteAsignaturaRepository.findByDocente(docente)
                .stream()
                .anyMatch(da -> da.getAsignatura().getAreaCurri().getId().equals(area.getId()));
    }
    
}