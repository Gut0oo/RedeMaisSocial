package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "TB_PERFIL_CANDIDATO")
public class PerfilCandidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerfil;
    private String objetivoProfissional;
    private String resumoProfissional;

    @OneToMany
    @JoinColumn(name = "id_perfil")
    private List<Habilidade> habilidades = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "id_perfil")
    private List<Interesse> interesses = new ArrayList<>();

    public void addHabilidade(Habilidade habilidade) {
        this.habilidades.add(habilidade);
        habilidade.setPerfil(this);
    }

    public void removeHabilidade(Habilidade habilidade) {
        this.habilidades.remove(habilidade);
        habilidade.setPerfil(null);
    }


    public void addInteresse(Interesse interesse) {
        this.interesses.add(interesse);
        interesse.setPerfilCandidato(this);
    }

    public void removeInteresse(Interesse interesse) {
        this.interesses.remove(interesse);
        interesse.setPerfilCandidato(null);
    }

}
