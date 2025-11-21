package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_CONTATO")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "localizacao_id", nullable = false)
    private Localizacao localizacao;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "telefone_id", nullable = false)
    private Telefone telefone;

    @ManyToOne
    @JoinColumn(name = "entidade_id", nullable = false)
    private Entidade entidade;

}
