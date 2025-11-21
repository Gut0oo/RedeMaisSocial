package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_HABILIDADES")
public class Habilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHabilidade;
    private String descricaoHabilidade;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private PerfilCandidato perfil;

}
