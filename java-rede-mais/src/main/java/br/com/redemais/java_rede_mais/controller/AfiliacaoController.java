package br.com.redemais.java_rede_mais.controller;

import br.com.redemais.java_rede_mais.DTO.request.AfiliacaoPFRequestDTO;
import br.com.redemais.java_rede_mais.DTO.request.VerificaCadastroRequestDTO;
import br.com.redemais.java_rede_mais.service.AfiliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastro")
public class AfiliacaoController {

    @Autowired
    private AfiliacaoService cadastroService;

    @PostMapping("/verificar-usuario")
    public ResponseEntity<String> verificarUsuario(@RequestBody VerificaCadastroRequestDTO request) {

        boolean estaRegistrado =  cadastroService.verificaUsuario(request);

        if (!estaRegistrado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Usuário Encontrado!");
    }


    @PostMapping("/afiliar-pessoa-fisica")
    public ResponseEntity<?> verificarUsuario(@RequestBody AfiliacaoPFRequestDTO request) {

        var afiliacao  = cadastroService.afiliaPessoaFisica(request);

        if (afiliacao == null){
            return ResponseEntity.badRequest().body("Erro ao cadastrar afiliacão!");
        }
        return ResponseEntity.ok(afiliacao);
    }


}
