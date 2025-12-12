package com.agendador.usuario.business;

import com.agendador.usuario.controller.DTO.UsuarioDTO;
import com.agendador.usuario.controller.converter.UsuarioConverter;
import com.agendador.usuario.infrastructure.entity.Usuario;
import com.agendador.usuario.infrastructure.exceptions.ConflictException;
import com.agendador.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        try{
            emailExiste(usuarioDTO.getEmail());
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            usuario = usuarioRepository.save(usuario);
            return  usuarioConverter.paraUsuarioDTO(usuario);
        }catch(ConflictException e){
            throw new ConflictException("Email já existente: " + e.getCause());
        }
    }

    //Funcionalidade que verifica dentro do banco de dados se o email é existente
    public boolean verificaEmailExiste(String email){
        return usuarioRepository.existsByEmail(email);
    }

    //Funcionalidade que chama o metodo verificaEmail e faz os tratamentos necessários
    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExiste(email);
            if (existe){
                throw new ConflictException("Email já cadastrado!");
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

}
