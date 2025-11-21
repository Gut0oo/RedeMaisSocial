package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_FORMACAO")
public class Formacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_formacao;
    private String curso;
    private String nivel;
    private String instituicao;
    private String dataInicio;
    private String dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_fisica_id")
    private PessoaFisica pessoaFisica;

}
