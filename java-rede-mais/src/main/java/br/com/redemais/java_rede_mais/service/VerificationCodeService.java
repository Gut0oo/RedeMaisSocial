package br.com.redemais.java_rede_mais.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificationCodeService {

    private static final int EXPIRATION_MINUTES = 5;

    private String codigoAtual;
    private String emailDestino;
    private LocalDateTime expiracao;

    public String gerarCodigo(String email) {
        this.codigoAtual = String.format("%06d", new Random().nextInt(999999));
        this.emailDestino = email;
        this.expiracao = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
        return codigoAtual;
    }

    public boolean validarCodigo(String email, String codigoDigitado) {
        if (codigoAtual == null) return false;

        if (!email.equals(emailDestino)) return false;

        if (LocalDateTime.now().isAfter(expiracao)) return false;

        return codigoAtual.equals(codigoDigitado);
    }
}
