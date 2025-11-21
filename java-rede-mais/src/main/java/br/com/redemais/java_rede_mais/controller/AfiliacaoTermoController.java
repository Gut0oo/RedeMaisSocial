package br.com.redemais.java_rede_mais.controller;

import br.com.redemais.java_rede_mais.DTO.request.TermoAfiliacaoResponseDTO;
import br.com.redemais.java_rede_mais.DTO.request.VerificacaoRequest;
import br.com.redemais.java_rede_mais.service.AfiliacaoTermoService;
import br.com.redemais.java_rede_mais.service.EmailService;
import br.com.redemais.java_rede_mais.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/afiliacoes")
@RequiredArgsConstructor
public class AfiliacaoTermoController {

    @Autowired
    private final AfiliacaoTermoService afiliacaoTermoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/{afiliacaoId}/termo")
    public ResponseEntity<TermoAfiliacaoResponseDTO> gerarTermo(@PathVariable Long afiliacaoId) {
        TermoAfiliacaoResponseDTO dto = afiliacaoTermoService.gerarTermoParaAfiliacao(afiliacaoId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{afiliacaoId}/aprovacaoTermo")
    public ResponseEntity<?> aprovarTermo(@PathVariable Long afiliacaoId) {

        String email = "guto050506@gmail.com";

        String codigo = verificationCodeService.gerarCodigo(email);

        emailService.enviarEmailSimples(
                email,
                "verificação",
                "Seu código é: " + codigo
        );

        return ResponseEntity.ok("Código enviado");
    }

    @PostMapping("/verificarCodigo")
    public ResponseEntity<?> verificarCodigo(@RequestBody VerificacaoRequest req) {

        boolean valido = verificationCodeService.validarCodigo(req.email, req.codigo);

        return valido
                ? ResponseEntity.ok("Código correto!")
                : ResponseEntity.status(400).body("Código incorreto!");
    }
}
