package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Consentimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime dataConsentimento;

    // Relacionamentos conforme o diagrama (0..1 Afiliacao / 0..1 Candidato)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "afiliacao_id")
    private Afiliacao afiliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @OneToMany(mappedBy = "consentimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsentimentoItem> itens = new ArrayList<>();
}

