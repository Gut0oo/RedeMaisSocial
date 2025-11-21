package br.com.redemais.java_rede_mais.service;

import br.com.redemais.java_rede_mais.DTO.AfiliacaoDTO;
import br.com.redemais.java_rede_mais.DTO.request.AfiliacaoPFRequestDTO;
import br.com.redemais.java_rede_mais.DTO.request.VerificaCadastroRequestDTO;
import br.com.redemais.java_rede_mais.entity.*;
import br.com.redemais.java_rede_mais.repository.AfiliacaoRepository;
import br.com.redemais.java_rede_mais.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfiliacaoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private AfiliacaoRepository afiliacaoRepository;

    public boolean verificaUsuario(VerificaCadastroRequestDTO request) {

        Candidato candidato = candidatoRepository.findByCpf(request.getCpf()).orElse(null);

        if (candidato == null) {
            return false;
        }
        return true;
    }

    public AfiliacaoDTO afiliaPessoaFisica(AfiliacaoPFRequestDTO request) {

        Telefone telefone = new Telefone();
        telefone.setTipo("Celular");
        telefone.setNumero(request.getTelefone());

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(request.getCidade());
        localizacao.setEstado(request.getEstado());
        localizacao.setCep(request.getCep());
        localizacao.setEndereco(request.getEndereco());

        Contato contato = new Contato();
        contato.setEmail(request.getEmail());
        contato.setTelefone(telefone);
        contato.setLocalizacao(localizacao);

        Identificacao identificacao = new Identificacao();
        identificacao.setSexo(request.getSexo());
        identificacao.setNascimento(request.getDataNascimento());
        identificacao.setIdade(request.getIdade());
        identificacao.setNacionalidade(request.getPais());
        identificacao.setProfissao(request.getProfissao());

        Formacao formacao = new Formacao();
        formacao.setCurso(request.getCurso());
        formacao.setNivel(request.getNivelFormacao());
        formacao.setNivel(request.getNivelFormacao());
        formacao.setInstituicao(request.getInstituicao());
        formacao.setDataConclusao(request.getDataConclusao());
        formacao.setDataInicio(request.getDataInicio());

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome(request.getNome());
        pessoaFisica.addContato(contato);
        pessoaFisica.setIdentificacao(identificacao);
        pessoaFisica.setSenha(request.getSenha());
        pessoaFisica.setStatus("Inativo");

        Habilidade habilidade = new Habilidade();
        habilidade.setDescricaoHabilidade(request.getHabilidades());

        Interesse interesse = new Interesse();
        interesse.setDescricaoInteresse(request.getAreaAtuacao());


        PerfilCandidato perfilCandidato = new PerfilCandidato();
        perfilCandidato.setResumoProfissional(request.getSobreMim());
        perfilCandidato.addHabilidade(habilidade);
        perfilCandidato.addInteresse(interesse);


        Candidato candidato = new Candidato();
        candidato.setCpf(request.getCpf());
        candidato.setPessoaFisica(pessoaFisica);

        Afiliacao afiliacao = new Afiliacao();
        afiliacao.setCandidato(candidato);
        afiliacao.setStatus("PENDENTE");

        var afiliacao_retorno = afiliacaoRepository.save(afiliacao);

        AfiliacaoDTO afiliacaoDTO = new AfiliacaoDTO();
        afiliacaoDTO = afiliacaoDTO.mapFromEntity(afiliacao_retorno);

        return afiliacaoDTO;
    }



}
