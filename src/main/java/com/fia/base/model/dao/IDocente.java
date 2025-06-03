package com.fia.base.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fia.base.model.entity.Docente;

public interface IDocente extends JpaRepository<Docente,Long>{
	 
}
