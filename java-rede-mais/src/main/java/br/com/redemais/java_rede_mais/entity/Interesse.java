package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_INTERESSES")
public class Interesse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInteresse;
    private String descricaoInteresse;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private PerfilCandidato perfilCandidato;

}
