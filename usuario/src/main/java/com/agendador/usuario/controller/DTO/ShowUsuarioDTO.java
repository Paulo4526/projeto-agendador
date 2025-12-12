package com.agendador.usuario.controller.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowUsuarioDTO {
    private String nome;
    private String email;
    private Integer idade;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;

    public ShowUsuarioDTO(UsuarioDTO usuarioDTO){
        this(usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getIdade(), usuarioDTO.getEnderecos(), usuarioDTO.getTelefones());
    }

}
