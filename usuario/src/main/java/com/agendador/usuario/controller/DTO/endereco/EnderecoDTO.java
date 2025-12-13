package com.agendador.usuario.controller.DTO.endereco;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Integer numero;
    private String complemento;
}
