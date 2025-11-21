package br.com.redemais.java_rede_mais.repository;

import br.com.redemais.java_rede_mais.entity.TermoDeUso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermoDeUsoRepository extends JpaRepository<TermoDeUso, Long> {
    Optional<TermoDeUso> findTopByStatusOrderByDataCriacaoDesc(String status);
}

