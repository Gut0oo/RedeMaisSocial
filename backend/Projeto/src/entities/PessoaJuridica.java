package entities;

import entities.PJ_Info.Empresa;

public class PessoaJuridica extends Entidade{
    private String cnpj;
    private Empresa empresa;

    public PessoaJuridica(String nome, int id, Contato contato, String cnpj, Empresa empresa) {
        super(nome, id, contato);
        this.cnpj = cnpj;
        this.empresa = empresa;
    }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}
