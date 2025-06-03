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
@Table(name= "Asignatura")
public class Asignatura {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_asig")
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "area_curri_id")
    private AreaCurri areaCurri;

  

    // Constructors
    public Asignatura() {
    }

    public Asignatura(String nombre, AreaCurri areaCurri) {
        this.nombre = nombre;
        this.areaCurri = areaCurri;
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

    public AreaCurri getAreaCurri() {
        return areaCurri;
    }

    public void setAreaCurri(AreaCurri areaCurri) {
        this.areaCurri = areaCurri;
    }
    
}
