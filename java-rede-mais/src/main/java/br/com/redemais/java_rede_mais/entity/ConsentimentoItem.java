package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ConsentimentoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean aceito;
    private LocalDateTime dataAceite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consentimento_id", nullable = false)
    private Consentimento consentimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_termo_id", nullable = false)
    private ItemTermo itemTermo;
}

