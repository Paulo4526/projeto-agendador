package com.agendador.usuario.controller.DTO.usuario;

import com.agendador.usuario.controller.DTO.endereco.EnderecoDTO;
import com.agendador.usuario.controller.DTO.endereco.ShowEnderecoDTO;
import com.agendador.usuario.controller.DTO.telefone.ShowTelefoneDTO;
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
    private List<ShowEnderecoDTO> enderecos;
    private List<ShowTelefoneDTO> telefones;

    public ShowUsuarioDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getIdade(),
                usuario.getEnderecos().stream().map(ShowEnderecoDTO::new).toList(),
                usuario.getTelefones().stream().map(ShowTelefoneDTO::new).toList()
        );
    }
}