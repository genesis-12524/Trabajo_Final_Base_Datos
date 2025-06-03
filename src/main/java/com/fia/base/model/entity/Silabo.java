package com.fia.base.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Silabo")
public class Silabo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sil")
	private Long id;
	private String ciclo, semestre, tipo_asig, tipo_est, moda, h_teo, h_prac, cred;

	@ManyToOne
	@JoinColumn(name = "ASIGNATURA_id_asig", referencedColumnName = "id_asig")
	private Asignatura asignatura;
	
	@ManyToOne
	@JoinColumn(name="docente_id")
	private Docente docente;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getTipo_asig() {
		return tipo_asig;
	}

	public void setTipo_asig(String tipo_asig) {
		this.tipo_asig = tipo_asig;
	}

	public String getTipo_est() {
		return tipo_est;
	}

	public void setTipo_est(String tipo_est) {
		this.tipo_est = tipo_est;
	}

	public String getModa() {
		return moda;
	}

	public void setModa(String moda) {
		this.moda = moda;
	}

	public String getH_teo() {
		return h_teo;
	}

	public void setH_teo(String h_teo) {
		this.h_teo = h_teo;
	}

	public String getH_prac() {
		return h_prac;
	}

	public void setH_prac(String h_prac) {
		this.h_prac = h_prac;
	}

	public String getCred() {
		return cred;
	}

	public void setCred(String cred) {
		this.cred = cred;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
		


}
