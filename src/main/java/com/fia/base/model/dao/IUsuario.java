package com.fia.base.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fia.base.model.entity.DocenteAsignatura;
import com.fia.base.model.entity.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByCorreo(String correo);
}
