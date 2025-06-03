package com.fia.base.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fia.base.model.entity.DocenteAsignatura;
import com.fia.base.model.entity.Rol;

public interface IRol extends JpaRepository<Rol, Long> {

}
