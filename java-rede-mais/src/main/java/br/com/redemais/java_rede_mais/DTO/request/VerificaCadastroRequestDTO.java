package br.com.redemais.java_rede_mais.DTO.request;

public class VerificaCadastroRequestDTO {
    private String cpf;
    private String email;

    //getters and setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

