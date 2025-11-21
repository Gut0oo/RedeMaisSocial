package br.com.redemais.java_rede_mais.repository;

import br.com.redemais.java_rede_mais.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Integer> {

    Optional<Candidato> findByCpf(String cpf);
}
