package entities.PF_Info;

import java.util.Date;

public class Identificacao {
    private int id, idade;
    private String sexo, profissao, nacionalidade;
    private Date aniversario;

    public Identificacao(Date aniversario, int id, int idade, String nacionalidade, String profissao, String sexo) {
        this.aniversario = aniversario;
        this.id = id;
        this.idade = idade;
        this.nacionalidade = nacionalidade;
        this.profissao = profissao;
        this.sexo = sexo;
    }

    public Identificacao(){

    }

    public Date getAniversario() { return aniversario; }
    public void setAniversario(Date aniversario) { this.aniversario = aniversario; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}
