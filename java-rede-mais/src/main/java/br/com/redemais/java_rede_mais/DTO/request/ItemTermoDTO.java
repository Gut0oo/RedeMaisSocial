package br.com.redemais.java_rede_mais.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemTermoDTO {
    private Long id;
    private String descricao;
    private boolean obrigatorio;
}
