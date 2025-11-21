package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_IDENTIFICACAO")
public class Identificacao {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id_identificacao;

    private int idade;
    private String sexo;
    private String profissao;
    private String estadoCivil;
    private String nacionalidade;
    private String nascimento;

    @OneToOne(mappedBy = "identificacao")
    @JoinColumn(name = "pessoa_fisica_id", nullable = false)
    private PessoaFisica pessoaFisica;

}
