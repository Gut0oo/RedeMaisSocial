package entities;

import entities.PF_Info.Formacao;
import entities.PF_Info.Identificacao;

public class PessoaFisica {
    private String cpf;
    private Formacao formacao;
    private Identificacao identificacao;

    PessoaFisica(){}

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Formacao getFormacao() { return formacao; }
    public void setFormacao(Formacao formacao) { this.formacao = formacao; }

    public Identificacao getIdentificacao() { return identificacao; }
    public void setIdentificacao(Identificacao identificacao) { this.identificacao = identificacao; }
}
