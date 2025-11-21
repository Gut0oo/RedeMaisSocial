package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_CANDIDATO")
@Data
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;

    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    private String representanteCnpj;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private PessoaFisica pessoaFisica;

    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Afiliacao afiliacao;
}
