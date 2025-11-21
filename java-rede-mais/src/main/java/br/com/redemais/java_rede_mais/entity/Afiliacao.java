package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_AFILIACAO")
@Data
public class Afiliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "DADOS", columnDefinition = "TEXT")
    private String dados;

    @Column(name = "STATUS", length = 30)
    private String status;

    @Column(name = "VALIDACAO", length = 50)
    private String validacao;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidato_id", unique = true, nullable = false)
    private Candidato candidato;

    @OneToMany(mappedBy = "afiliacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consentimento> consentimentos = new ArrayList<>();
}

