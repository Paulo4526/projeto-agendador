package com.agendador.usuario.controller;

import com.agendador.usuario.business.UsuarioService;
import com.agendador.usuario.controller.DTO.endereco.EnderecoDTO;
import com.agendador.usuario.controller.DTO.endereco.ShowEnderecoDTO;
import com.agendador.usuario.controller.DTO.login.LoginDTO;
import com.agendador.usuario.controller.DTO.telefone.ShowTelefoneDTO;
import com.agendador.usuario.controller.DTO.telefone.TelefoneDTO;
import com.agendador.usuario.controller.DTO.usuario.ShowUsuarioDTO;
import com.agendador.usuario.controller.DTO.login.TokenDTO;
import com.agendador.usuario.controller.DTO.usuario.UsuarioDTO;
import com.agendador.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PutMapping
    public ResponseEntity<ShowUsuarioDTO> atualizaUsuariov(@RequestHeader("Authorization") String token,@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<ShowEnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO, @RequestParam("enderecoId") UUID id){
        return ResponseEntity.ok(usuarioService.atualizandoEnderedo(id, enderecoDTO));
    }

    @GetMapping("/endereco")
    public ResponseEntity<ShowEnderecoDTO> buscaEnderecoById(@RequestParam("enderecoId") UUID id){
        return ResponseEntity.ok(usuarioService.buscaEnderecoById(id));
    }

    @DeleteMapping("/endereco")
    public ResponseEntity<Void> deleteEnderecoById(@RequestParam("enderecoId") UUID id){
        usuarioService.deleteEnderecoById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/endereco")
    public ResponseEntity<ShowEnderecoDTO> cadastraNovoEndereco(@RequestHeader("Authorization") String token, @RequestBody EnderecoDTO enderecoDTO){
        ShowEnderecoDTO salvo = usuarioService.cadastraNovoEndereco(token, enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/telefone")
    public ResponseEntity<ShowTelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO telefoneDTO, @RequestParam("telefoneId") UUID id){
        return ResponseEntity.ok(usuarioService.atualizandoTelefone(id, telefoneDTO));
    }

    @GetMapping("/telefone")
    public ResponseEntity<ShowTelefoneDTO> buscaTelefoneById(@RequestParam("telefoneId") UUID id){
        return ResponseEntity.ok(usuarioService.buscaTelefoneById(id));
    }

    @DeleteMapping("/telefone")
    public ResponseEntity<Void> deleteTelefoneById(@RequestParam("telefoneId") UUID id){
        usuarioService.deleteTelefoneBydId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/telefone")
    public ResponseEntity<ShowTelefoneDTO> cadastraNovoTelefone(@RequestHeader("Authorization") String token, @RequestBody TelefoneDTO telefoneDTO){
        ShowTelefoneDTO salvo = usuarioService.cadastraNovoTelefone(token, telefoneDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

}
