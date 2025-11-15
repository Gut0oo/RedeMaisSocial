package entities;

public class Entidade {
    private int id;
    private String nome;
    private Contato contato;

    public Entidade(){

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome;}

    public Contato getContato(){ return contato; }
    public void setContato(Contato contato) { this.contato = contato; }
}
