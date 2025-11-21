package br.com.redemais.java_rede_mais.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TermoAfiliacaoResponseDTO {
    private Long afiliacaoId;
    private Long termoDeUsoId;
    private Long consentimentoId;
    private String titulo;
    private String versao;
    private String link;
    private List<ItemTermoDTO> itens;
}


