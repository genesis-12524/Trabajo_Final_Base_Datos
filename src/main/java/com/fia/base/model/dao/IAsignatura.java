package com.fia.base.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fia.base.model.entity.AreaCurri;
import com.fia.base.model.entity.Asignatura;

public interface IAsignatura extends  JpaRepository<Asignatura,Long>{
	 List<Asignatura> findByAreaCurri(AreaCurri areaCurri);
}
