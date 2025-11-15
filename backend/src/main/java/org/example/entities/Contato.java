package org.example.entities;

import entities.contatoInfo.Localizacao;
import entities.contatoInfo.Telefone;

public class Contato {
    private int id;
    private String email;
    private Localizacao loc;
    private Telefone tel;

    public Contato(){}

    public Contato(int id, Localizacao loc, Telefone tel, String email) {
        this.id = id;
        this.email = email;
        this.loc = loc;
        this.tel = tel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Localizacao getLoc() { return loc; }
    public void setLoc(Localizacao loc) { this.loc = loc; }

    public Telefone getTel() { return tel; }
    public void setTel(Telefone tel) { this.tel = tel; }
}
