package com.realfaceapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realfaceapi.enums.PerfilEnum;
import com.realfaceapi.models.Usuario;
import com.realfaceapi.repository.UsuarioRepository;
import com.realfaceapi.utils.SenhaUtils;;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository ur;

	// Salva um usuário no banco de dados.

	@PostMapping("/cadastrar")
	@ResponseBody
	Usuario novoUsuario(@RequestBody Usuario usuario) {
		usuario.setSenha(SenhaUtils.gerarBCrypt(usuario.getSenha()));
		usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
		return ur.save(usuario);
	}

	// Retorna todos os usuários cadastrados no banco de dados.

	@GetMapping
	public @ResponseBody Iterable<Usuario> listaUsuarios() {
		Iterable<Usuario> listaUsuarios = ur.findAll();
		return listaUsuarios;
	}
	
}