package br.com.redemais.java_rede_mais.DTO;

import br.com.redemais.java_rede_mais.entity.Afiliacao;
import lombok.Data;

@Data
public class AfiliacaoDTO {
    private Long id;
    private String status;

    private CandidatoDTO candidato;

    //aplica um mapper do entidade Afiliacao para AfiliacaoDTO
    public AfiliacaoDTO mapFromEntity(Afiliacao afiliacao) {
        AfiliacaoDTO dto = new AfiliacaoDTO();
        dto.setId(afiliacao.getId());
        dto.setStatus(afiliacao.getStatus());
        //mapeia o candidato
        CandidatoDTO candidatoDTO = new CandidatoDTO();
        candidatoDTO.setId(afiliacao.getCandidato().getId());
        candidatoDTO.setCpf(afiliacao.getCandidato().getCpf());
        candidatoDTO.setRepresentanteCnpj(afiliacao.getCandidato().getRepresentanteCnpj());
        //mapeia a pessoa fisica
        br.com.redemais.java_rede_mais.mapper.PessoaFisicaDTO pessoaFisicaDTO = new br.com.redemais.java_rede_mais.mapper.PessoaFisicaDTO();
        pessoaFisicaDTO.setId(afiliacao.getCandidato().getPessoaFisica().getId());
        pessoaFisicaDTO.setNome(afiliacao.getCandidato().getPessoaFisica().getNome());
        candidatoDTO.setPessoaFisica(pessoaFisicaDTO);
        dto.setCandidato(candidatoDTO);
        return dto;
    }

}
