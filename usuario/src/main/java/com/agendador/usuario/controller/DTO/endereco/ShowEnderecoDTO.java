package com.agendador.usuario.controller.DTO.endereco;

import com.agendador.usuario.infrastructure.entity.Endereco;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowEnderecoDTO {
    private UUID id;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Integer numero;
    private String complemento;
    public ShowEnderecoDTO(Endereco endereco){
        this(endereco.getId(), endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado(), endereco.getCep(), endereco.getNumero(), endereco.getComplemento());
    }
}
