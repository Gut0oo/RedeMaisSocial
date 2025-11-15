package entities.PJ_Info;

import entities.PessoaFisica;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private int id;
    private PessoaFisica representanteLegal;
    private String razaoSocial;

    private List<Certidao> certidoes; //um array de certidoes

    public Empresa(int id, String razaoSocial, PessoaFisica representanteLegal) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.representanteLegal = representanteLegal;
        this.certidoes = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public PessoaFisica getRepresentanteLegal() { return representanteLegal; }
    public void setRepresentanteLegal(PessoaFisica representanteLegal) { this.representanteLegal = representanteLegal; }

    public List<Certidao> getCertidoes(){ return certidoes; }

    public void addCertidao(Certidao c) {
        certidoes.add(c);
    }
    public void removeCertidao(Certidao c) {
        certidoes.remove(c);
    }
}
