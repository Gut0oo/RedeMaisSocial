package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("PF")
public class PessoaFisica extends Entidade {

    public PessoaFisica() {
        super();
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Identificacao identificacao;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Formacao> formacao = new ArrayList<>();

    public void addFormacao(Formacao formacao) {
        this.formacao.add(formacao);
        formacao.setPessoaFisica(this);
    }

    public void removeFormacao(Formacao formacao) {
        this.formacao.remove(formacao);
        formacao.setPessoaFisica(null);
    }

}
