package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ItemTermo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private boolean ehObrigatorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "termo_de_uso_id", nullable = false)
    private TermoDeUso termoDeUso;
}
