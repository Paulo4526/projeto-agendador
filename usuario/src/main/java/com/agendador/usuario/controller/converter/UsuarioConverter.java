package com.agendador.usuario.controller.converter;

import com.agendador.usuario.controller.DTO.endereco.EnderecoDTO;
import com.agendador.usuario.controller.DTO.usuario.ShowUsuarioDTO;
import com.agendador.usuario.controller.DTO.telefone.TelefoneDTO;
import com.agendador.usuario.controller.DTO.usuario.UsuarioDTO;
import com.agendador.usuario.infrastructure.entity.Endereco;
import com.agendador.usuario.infrastructure.entity.Telefone;
import com.agendador.usuario.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//OBS: Esta maneira é feita devido não haver as funcionalidades da classe record no java 11, então neste microserviço será feito com metodos converter para salvar os dados nas entidades de Usuario e ShowUsuarioDTO
@Component
@RequiredArgsConstructor
public class UsuarioConverter {

    private final PasswordEncoder passwordEncoder;

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(encodePassword(usuarioDTO.getSenha()))
                .idade(usuarioDTO.getIdade())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .senha(usuarioDTO.getSenha() != null ? encodePassword(usuarioDTO.getSenha()) : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .idade(usuarioDTO.getIdade() != null ? usuarioDTO.getIdade() : entity.getIdade())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();

    }

    //Cria endereco quando o usuario for criado
    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }

    //Adiciona novos endereços após a criação do usuario
    public Endereco paraEnderecoEntity(UUID id, EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .user_id(id)
                .build();
    }

    //Iteração com stream().map()
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTO){
        return enderecoDTO.stream().map(this::paraEndereco).toList();
    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco endereco){
        return Endereco.builder()
                .id(endereco.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : endereco.getRua())
                .bairro(enderecoDTO.getBairro() != null ? enderecoDTO.getBairro() : endereco.getBairro())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : endereco.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : endereco.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : endereco.getCep())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : endereco.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : endereco.getComplemento())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone telefone){
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefone.getDdd())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefone.getNumero())
                .build();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    public Telefone paraTelefoneEntity(UUID id, TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .user_id(id)
                .build();
    }

    //Iteração em metodo for
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
        List<Telefone> telefones = new ArrayList<>();
        for(TelefoneDTO telefoneDTO : telefoneDTOS){
            telefones.add(paraTelefone(telefoneDTO));
        }

        return telefones;
    }



    //Metodo que escriptara o password
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }





}
