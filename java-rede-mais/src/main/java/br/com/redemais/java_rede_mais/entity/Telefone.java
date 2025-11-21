package br.com.redemais.java_rede_mais.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_TELEFONE")
@Data
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_telefone;
    private String numero;
    private String tipo;
}
