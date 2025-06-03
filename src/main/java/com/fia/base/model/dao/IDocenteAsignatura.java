package com.fia.base.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fia.base.model.entity.Asignatura;
import com.fia.base.model.entity.Docente;
import com.fia.base.model.entity.DocenteAsignatura;

public interface IDocenteAsignatura extends JpaRepository<DocenteAsignatura, Long> {
	 List<DocenteAsignatura> findByAsignatura(Asignatura asignatura);
	 List<DocenteAsignatura> findByDocente(Docente docente);
}