package com.fia.base.model.DTO;

import java.util.List;

import com.fia.base.model.entity.Docente;

public class AsignConDocesDTO {
	 private String nombreAsignatura;
	 
	    private List<Docente> docentes;

		public String getNombreAsignatura() {
			return nombreAsignatura;
		}

		public void setNombreAsignatura(String nombreAsignatura) {
			this.nombreAsignatura = nombreAsignatura;
		}

		public List<Docente> getDocentes() {
			return docentes;
		}

		public void setDocentes(List<Docente> docentes) {
			this.docentes = docentes;
		}

		public AsignConDocesDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		public AsignConDocesDTO(String nombreAsignatura, List<Docente> docentes) {
			super();
			this.nombreAsignatura = nombreAsignatura;
			this.docentes = docentes;
		}
	    
}
