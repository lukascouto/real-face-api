package com.realfaceapi.repository;

import org.springframework.data.repository.CrudRepository;
import com.realfaceapi.models.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, String> {
	Usuario findById(Long id);


	Usuario findByEmail(String email);
	
}

