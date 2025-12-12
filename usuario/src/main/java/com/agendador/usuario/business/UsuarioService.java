package com.agendador.usuario.business;

import com.agendador.usuario.controller.DTO.ShowUsuarioDTO;
import com.agendador.usuario.controller.DTO.UsuarioDTO;
import com.agendador.usuario.controller.converter.UsuarioConverter;
import com.agendador.usuario.infrastructure.entity.Usuario;
import com.agendador.usuario.infrastructure.exceptions.ConflictException;
import com.agendador.usuario.infrastructure.exceptions.ResourceNotFoundException;
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

    public ShowUsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        try{
            //Verifica se o e-maile xiste
            emailExiste(usuarioDTO.getEmail());
            //Encrypta o password recebido
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            //Recebe os valores do usuário DTO e copia para a entidade Usuário
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            //Salva os dados recebidos pela entidade DTO no banco de dados
            usuario = usuarioRepository.save(usuario);
            //Retorna o ShowUsuarioDTO onde não mostrará a senha do usuário
            return usuarioConverter.paraUsuarioDTO(usuario);

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

    public void deleteUserByEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public ShowUsuarioDTO findUserByEmail(String email){
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email de usuário não encontrado!")));
    }

}
