package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_LOCALIZACAO")
public class Localizacao {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id_localizacao;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
}
