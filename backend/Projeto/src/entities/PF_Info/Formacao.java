package entities.PF_Info;

import enums.StatusCurso;

public class Formacao {
    private int id, ano;
    private String faculdade, curso;
    private StatusCurso status;

    public Formacao(int ano, String curso, String faculdade, int id, StatusCurso status) {
        this.ano = ano;
        this.curso = curso;
        this.faculdade = faculdade;
        this.id = id;
        this.status = status;
    }

    public Formacao(){

    }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getFaculdade() { return faculdade; }
    public void setFaculdade(String faculdade) { this.faculdade = faculdade;}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public StatusCurso getStatus() { return status; }
    public void setStatus(StatusCurso status) { this.status = status; }
}
