package com.fia.base.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "AreaCurri")
public class AreaCurri {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nombre;

	    @OneToMany(mappedBy = "areaCurri", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Asignatura> asignaturas;

	    // Constructors
	    public AreaCurri() {
	    }

	    public AreaCurri(String nombre) {
	        this.nombre = nombre;
	    }

	    // Getters y Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public List<Asignatura> getAsignaturas() {
	        return asignaturas;
	    }

	    public void setAsignaturas(List<Asignatura> asignaturas) {
	        this.asignaturas = asignaturas;
	    }
}
