package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Link extends Elemento {

    private String url;
    private String descricao;
    private String alvo;
}

