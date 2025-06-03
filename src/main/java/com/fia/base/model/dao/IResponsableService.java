package com.fia.base.model.dao;

import java.util.List;
import java.util.Map;

import com.fia.base.model.entity.Docente;

public interface IResponsableService {
	Map<String, List<Docente>> obtenerTodosResponsables();
}
