package com.agendador.usuario.controller.DTO.usuario;

import com.agendador.usuario.controller.DTO.endereco.EnderecoDTO;
import com.agendador.usuario.controller.DTO.telefone.TelefoneDTO;
import com.agendador.usuario.infrastructure.entity.Endereco;
import com.agendador.usuario.infrastructure.entity.Telefone;
import com.agendador.usuario.infrastructure.entity.Usuario;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowUsuarioDTO {

    private UUID id;
    private String nome;
    private String email;
    private Integer idade;
    private List<Endereco> enderecos;
    private List<Telefone> telefones;

    public ShowUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getIdade(), usuario.getEnderecos(), usuario.getTelefones());
    }
}