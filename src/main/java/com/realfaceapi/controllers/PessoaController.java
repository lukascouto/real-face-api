package com.realfaceapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realfaceapi.models.Pessoa;
import com.realfaceapi.models.Usuario;
import com.realfaceapi.repository.PessoaRepository;
import com.realfaceapi.repository.UsuarioRepository;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private PessoaRepository pr;
	
	// Salva uma pessoa no banco de dados passando o id do usuário da API.
	
	@PostMapping("/{id}")
	public @ResponseBody String novaPessoa(@PathVariable("id") long id, @RequestBody Pessoa pessoa) {
		Usuario usuario = ur.findById(id);
		pessoa.setIdUsuario(usuario.getId());
		pr.save(pessoa);
		return pessoa.getId().toString();
	}
	
	// Retorna uma lista de pessoas pelo id do usuário da API.
	
	@GetMapping("/{id}")
	public @ResponseBody List<Pessoa>
	  getById(@PathVariable long id, Pessoa pessoa) {	
		List<Pessoa> listaPessoas = pr.findByIdUsuario(id);
		return listaPessoas;
	}
}