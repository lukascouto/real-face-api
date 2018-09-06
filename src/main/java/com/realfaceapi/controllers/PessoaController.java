package com.realfaceapi.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realfaceapi.models.Pessoa;
import com.realfaceapi.models.Usuario;
import com.realfaceapi.repository.PessoaRepository;
import com.realfaceapi.repository.UsuarioRepository;
import com.realfaceapi.response.Response;
import com.realfaceapi.security.utils.JwtTokenUtil;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private PessoaRepository pr;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	// Salva uma pessoa no banco de dados passando o id do usuário da API.

	@PostMapping("/salvar")
	public @ResponseBody Long novaPessoa(HttpServletRequest request, @RequestBody Pessoa pessoa) {
		Response<Long> response = new Response<>();

		// Obtém o email pelo token JWT a ser extraído do header
		String token = request.getHeader(AUTH_HEADER);
		if (token != null && token.startsWith(BEARER_PREFIX)) {
			token = token.substring(7);
		}
		String email = jwtTokenUtil.getUsernameFromToken(token);

		// Carrega o usuário por email, obtido no token
		Optional<Usuario> usuario = Optional.ofNullable(ur.findByEmail(email));
		
		if (!usuario.isPresent()) {
			response.getErrors().add("Usuário não encontrado.");
			ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		pessoa.setIdUsuario(usuario.get().getId());
		pr.save(pessoa);
		return pessoa.getId();
		
		
		// depois tentar usar com o response
		//response.setData(pessoa.getId());
		//return ResponseEntity.ok(response);
		
	}

	// Retorna uma lista de pessoas correspondentes ao token do usuário

	@GetMapping("/buscar")
	public @ResponseBody List<Pessoa> getUsuarioId(HttpServletRequest request) {
		Response<Long> response = new Response<>();

		// Obtém o email pelo token JWT a ser extraído do header
		String token = request.getHeader(AUTH_HEADER);
		if (token != null && token.startsWith(BEARER_PREFIX)) {
			token = token.substring(7);
		}
		String email = jwtTokenUtil.getUsernameFromToken(token);

		// Carrega o usuário por email, obtido no token
		Optional<Usuario> usuario = Optional.ofNullable(ur.findByEmail(email));

		if (!usuario.isPresent()) {
			response.getErrors().add("Usuário não encontrado.");
			ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		List<Pessoa> listaPessoas = pr.findByIdUsuario(usuario.get().getId());
		return listaPessoas;
		// response.setData(usuario.get().getId());
		// return ResponseEntity.ok(response);
	}
}