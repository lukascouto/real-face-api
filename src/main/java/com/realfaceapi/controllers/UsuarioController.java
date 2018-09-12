package com.realfaceapi.controllers;

import com.realfaceapi.response.Response;
import com.realfaceapi.security.dto.UsuarioDto;
import com.realfaceapi.security.utils.JwtTokenUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realfaceapi.enums.PerfilEnum;
import com.realfaceapi.models.Usuario;
import com.realfaceapi.repository.UsuarioRepository;
import com.realfaceapi.utils.SenhaUtils;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ModelMapper modelMapper;

	// Salva um usuário no banco de dados.

	@PostMapping("/salvar")
	@ResponseBody
	ResponseEntity<Response<UsuarioDto>> novoUsuario(@RequestBody UsuarioDto usuarioDto) throws ParseException {
		Response<UsuarioDto> response = new Response<>();
		String email = usuarioDto.getEmail();
		Optional<Usuario> u = Optional.ofNullable(ur.findByEmail(email));

		// Caso já exista um usuário com o mesmo e-mail, o novo cadastro é negado
		if (u.isPresent()) {
			response.getErrors().add("Já existe um cadastro com este e-mail.");

		// Caso o e-mail ainda não tenha sido utilizado, um novo cadastro será feito
		} else {

			Usuario usuario = convertToEntity(usuarioDto);
			usuario.setSenha(SenhaUtils.gerarBCrypt(usuario.getSenha()));
			usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
			ur.save(usuario);
		}
		response.setData(new UsuarioDto(usuarioDto.getNome(), usuarioDto.getEmail(), usuarioDto.getSenha()));
		return ResponseEntity.ok(response);
	}

	// DTO para Entidade Java
	private Usuario convertToEntity(UsuarioDto usuarioDto) throws ParseException {
		Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
		return usuario;
	}
	// Fim da conversão

	// Retorna todos os usuários cadastrados no banco de dados.

	@GetMapping
	public @ResponseBody Iterable<Usuario> listaUsuarios() {
		Iterable<Usuario> listaUsuarios = ur.findAll();
		return listaUsuarios;
	}

	// Retorna o id do usuário que fez a requisição

	@GetMapping("/id")
	public @ResponseBody Long getUsuarioId(HttpServletRequest request) {
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

		return usuario.get().getId();
	}

	/*
	 * MÉTODO PARA RETORNAR O ID DO USUÁRIO DA API.
	 * 
	 * @GetMapping("/id") public ResponseEntity<Response<Long>>
	 * getUsuarioId(HttpServletRequest request) { Response<Long> response = new
	 * Response<>();
	 * 
	 * // Obtém o email pelo token JWT a ser extraído do header String token =
	 * request.getHeader(AUTH_HEADER); if (token != null &&
	 * token.startsWith(BEARER_PREFIX)) { token = token.substring(7); } String email
	 * = jwtTokenUtil.getUsernameFromToken(token);
	 * 
	 * // Carrega o usuário por email, obtido no token Optional<Usuario> usuario =
	 * Optional.ofNullable(ur.findByEmail(email));
	 * 
	 * if (!usuario.isPresent()) {
	 * response.getErrors().add("Usuário não encontrado.");
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); }
	 * 
	 * response.setData(usuario.get().getId()); return ResponseEntity.ok(response);
	 * }
	 * 
	 */

}