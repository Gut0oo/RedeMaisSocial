package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("PJ")
public class PessoaJuridica extends Entidade {
    private String ramoAtividade;
    private String inscricaoEstadual;

}
