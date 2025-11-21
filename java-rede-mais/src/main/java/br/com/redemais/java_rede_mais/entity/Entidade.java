package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Table(name = "TB_ENTIDADE")
public class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String senha;
    private String status;


    @OneToMany(
            mappedBy = "entidade",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Contato> contatos = new ArrayList<>();

    public void addContato(Contato contato) {
        contatos.add(contato);
        contato.setEntidade(this);
    }

    public void removeContato(Contato contato) {
        contatos.remove(contato);
        contato.setEntidade(null);
    }

}