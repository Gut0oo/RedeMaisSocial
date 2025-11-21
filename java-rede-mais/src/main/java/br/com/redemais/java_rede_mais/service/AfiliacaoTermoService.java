package br.com.redemais.java_rede_mais.service;

import br.com.redemais.java_rede_mais.DTO.request.ItemTermoDTO;
import br.com.redemais.java_rede_mais.DTO.request.TermoAfiliacaoResponseDTO;
import br.com.redemais.java_rede_mais.entity.Afiliacao;
import br.com.redemais.java_rede_mais.entity.Consentimento;
import br.com.redemais.java_rede_mais.entity.ConsentimentoItem;
import br.com.redemais.java_rede_mais.entity.TermoDeUso;
import br.com.redemais.java_rede_mais.repository.AfiliacaoRepository;
import br.com.redemais.java_rede_mais.repository.ConsentimentoRepository;
import br.com.redemais.java_rede_mais.repository.TermoDeUsoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AfiliacaoTermoService {

    private final AfiliacaoRepository afiliacaoRepository;
    private final TermoDeUsoRepository termoDeUsoRepository;
    private final ConsentimentoRepository consentimentoRepository;

    @Transactional
    public TermoAfiliacaoResponseDTO gerarTermoParaAfiliacao(Long afiliacaoId) {

        Afiliacao afiliacao = afiliacaoRepository.findById(afiliacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Afiliação não encontrada: " + afiliacaoId));

        TermoDeUso termoDeUso = termoDeUsoRepository
                .findTopByStatusOrderByDataCriacaoDesc("ATIVO")
                .orElseThrow(() -> new IllegalStateException("Nenhum termo de uso ATIVO encontrado"));

        // Cria o consentimento para esta afiliação
        Consentimento consentimento = new Consentimento();
        consentimento.setStatus("PENDENTE");
        consentimento.setDataConsentimento(null); // será preenchido quando aceitar
        consentimento.setAfiliacao(afiliacao);

        // Cria itens de consentimento (um para cada ItemTermo do TermoDeUso)
        Consentimento finalConsentimento = consentimento;
        List<ConsentimentoItem> itensConsentimento = termoDeUso.getItens().stream()
                .map(itemTermo -> {
                    ConsentimentoItem ci = new ConsentimentoItem();
                    ci.setAceito(false);
                    ci.setDataAceite(null);
                    ci.setConsentimento(finalConsentimento);
                    ci.setItemTermo(itemTermo);
                    return ci;
                })
                .collect(Collectors.toList());

        consentimento.setItens(itensConsentimento);

        consentimento = consentimentoRepository.save(consentimento);

        // Monta DTO de retorno
        List<ItemTermoDTO> itensDTO = termoDeUso.getItens().stream()
                .map(it -> new ItemTermoDTO(
                        it.getId(),
                        it.getDescricao(),
                        it.isEhObrigatorio()
                ))
                .collect(Collectors.toList());

        return new TermoAfiliacaoResponseDTO(
                afiliacao.getId(),
                termoDeUso.getId(),
                consentimento.getId(),
                termoDeUso.getTitulo(),
                termoDeUso.getVersao(),
                termoDeUso.getLink(),
                itensDTO
        );
    }
}

