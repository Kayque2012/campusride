package com.campusride.repository;

import com.campusride.domain.Carona;
import com.campusride.domain.enums.SituacaoCarona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaronaRepository extends JpaRepository<Carona, Long> {

    List<Carona> findBySituacao(SituacaoCarona situacao);
}
