package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class TermoDeUso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String link;
    private String versao;
    private String status;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "termoDeUso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTermo> itens = new ArrayList<>();


}