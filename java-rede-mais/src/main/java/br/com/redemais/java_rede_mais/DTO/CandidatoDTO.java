package br.com.redemais.java_rede_mais.DTO;

import br.com.redemais.java_rede_mais.mapper.PessoaFisicaDTO;
import lombok.Data;

@Data
public class CandidatoDTO {
    private int id;
    private String cpf;
    private String representanteCnpj;
    private PessoaFisicaDTO pessoaFisica;

}
