package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Texto extends Elemento {

    private String descricao;
    private String formatacao;
}
