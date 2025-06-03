package com.fia.base.model.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
@Entity
@Table(name= "docente")
public class Docente {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nom;
	    private String ape;
	    private String gradoA;

	    

	    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Silabo> silabos;

	    
	    private String estado_encargado;
	    public String getEstado_encargado() {
			return estado_encargado;
		}

		public void setEstado_encargado(String estado_encargado) {
			this.estado_encargado = estado_encargado;
		}

		// Constructors
	    public Docente() {
	    }

	    public Docente(String nom, String ape, String gradoA) {
	        this.nom = nom;
	        this.ape = ape;
	        this.gradoA = gradoA;
	    }

	    // Getters y Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getNom() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    public String getApe() {
	        return ape;
	    }

	    public void setApe(String ape) {
	        this.ape = ape;
	    }

	    public String getGradoA() {
	        return gradoA;
	    }

	    public void setGradoA(String gradoA) {
	        this.gradoA = gradoA;
	    }

	  

	    public List<Silabo> getSilabos() {
	        return silabos;
	    }

	    public void setSilabos(List<Silabo> silabos) {
	        this.silabos = silabos;
	    }

}
