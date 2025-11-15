package entities.contatoInfo;

public class Localizacao {
    private String cep, cidade, estado, pais, logradouro;
    private int num, id;

    public Localizacao(){}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }


    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
}
