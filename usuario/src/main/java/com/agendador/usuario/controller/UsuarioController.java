package com.agendador.usuario.controller;

import com.agendador.usuario.business.UsuarioService;
import com.agendador.usuario.controller.DTO.LoginDTO;
import com.agendador.usuario.controller.DTO.ShowUsuarioDTO;
import com.agendador.usuario.controller.DTO.TokenDTO;
import com.agendador.usuario.controller.DTO.UsuarioDTO;
import com.agendador.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ShowUsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        ShowUsuarioDTO salvo = usuarioService.salvaUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
        );
        return ResponseEntity.ok(new TokenDTO(jwtUtil.generateToken(authentication.getName())));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ShowUsuarioDTO> findUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(usuarioService.findUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email){
        usuarioService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

}
