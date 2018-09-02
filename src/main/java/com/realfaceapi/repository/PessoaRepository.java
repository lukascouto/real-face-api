package com.realfaceapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.realfaceapi.models.Pessoa;

import java.lang.Long;
import java.util.List;

public interface PessoaRepository extends CrudRepository<Pessoa, String> {
	
	List<Pessoa> findByIdUsuario(Long idusuario);
	
	
}