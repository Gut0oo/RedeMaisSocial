package entities.PJ_Info;

import enums.StatusCertidao;

import java.time.LocalDate;

public class Certidao {
    private int id;
    private LocalDate dataEmissao;
    private LocalDate dataValidade;
    private StatusCertidao status;
    private String linkArquivo;
    private String orgaoEmissor;
    private String tipoCertidao;

    public Certidao(String tipoCertidao, StatusCertidao status, String orgaoEmissor, String linkArquivo, int id, LocalDate dataValidade, LocalDate dataEmissao) {
        this.tipoCertidao = tipoCertidao;
        this.status = status;
        this.orgaoEmissor = orgaoEmissor;
        this.linkArquivo = linkArquivo;
        this.id = id;
        this.dataValidade = dataValidade;
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLinkArquivo() { return linkArquivo; }
    public void setLinkArquivo(String linkArquivo) { this.linkArquivo = linkArquivo; }

    public String getOrgaoEmissor() { return orgaoEmissor; }
    public void setOrgaoEmissor(String orgaoEmissor) { this.orgaoEmissor = orgaoEmissor; }

    public StatusCertidao getStatus() { return status; }
    public void setStatus(StatusCertidao status) { this.status = status; }

    public String getTipoCertidao() { return tipoCertidao; }
    public void setTipoCertidao(String tipoCertidao) { this.tipoCertidao = tipoCertidao; }
}
