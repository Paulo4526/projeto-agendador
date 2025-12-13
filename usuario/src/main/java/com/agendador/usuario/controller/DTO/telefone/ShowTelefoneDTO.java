package com.agendador.usuario.controller.DTO.telefone;

import com.agendador.usuario.infrastructure.entity.Telefone;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowTelefoneDTO {
    private UUID id;
    private Integer numero;
    private Integer ddd;

    public ShowTelefoneDTO(Telefone telefone){
        this(telefone.getId(), telefone.getNumero(), telefone.getDdd());
    }
}
