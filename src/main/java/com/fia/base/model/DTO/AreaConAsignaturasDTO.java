package com.fia.base.model.DTO;

import java.util.List;

public class AreaConAsignaturasDTO {
	private String nombreArea;
    private List<AsignConDocesDTO> asignaturas;
    
    
	public String getNombreArea() {
		return nombreArea;
	}
	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}
	public List<AsignConDocesDTO> getAsignaturas() {
		return asignaturas;
	}
	public void setAsignaturas(List<AsignConDocesDTO> asignaturas) {
		this.asignaturas = asignaturas;
	}
	public AreaConAsignaturasDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
