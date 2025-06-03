package com.fia.base.model.entity;

import java.util.List;

public class AsignaturaConDocentesDTO {
	private Long asignaturaId;
    private String nombreAsignatura;
    private List<Docente> docentes;
    
    public AsignaturaConDocentesDTO(Long asignaturaId, String nombreAsignatura, List<Docente> docentes) {
        this.asignaturaId = asignaturaId;
        this.nombreAsignatura = nombreAsignatura;
        this.docentes = docentes;
    }
    
    // Getters y Setters
    public Long getAsignaturaId() { return asignaturaId; }
    public String getNombreAsignatura() { return nombreAsignatura; }
    public List<Docente> getDocentes() { return docentes; }

	public void setNombreAsignatura(String nombre) {
		// TODO Auto-generated method stub
		
	}
}
