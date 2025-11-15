package entities.contatoInfo;

import enums.Operadora;
import enums.TipoTelefone;

public class Telefone {
    private int id;

    private String ddd, numTel;
    private TipoTelefone tipoCel;
    private Operadora operadora;

    public Telefone(){}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDdd() { return ddd; }
    public void setDdd(String ddd) { this.ddd = ddd; }

    public String getNumTel() { return numTel; }
    public void setNumTel(String numTel) { this.numTel = numTel; }

    public TipoTelefone getTipoCel() { return tipoCel; }
    public void setTipoCel(TipoTelefone tipoCel) { this.tipoCel = tipoCel; }

    public Operadora getOperadora() { return operadora; }
    public void setOperadora(Operadora operadora) { this.operadora = operadora; }
}
