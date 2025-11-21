package br.com.redemais.java_rede_mais.DTO.request;

import lombok.Data;

@Data
public class AfiliacaoPJRequestDTO {
    private String email;
    private String cnpj;
    private String nome;
    private String telefone;
    private String nomeResponsavel;
    private String emailResponsavel;
    private String areaAtuacao;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
}
