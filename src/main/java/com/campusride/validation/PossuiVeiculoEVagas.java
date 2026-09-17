package com.campusride.validation;

import com.campusride.domain.enums.TipoVeiculo;


public interface PossuiVeiculoEVagas {

    TipoVeiculo getTipoVeiculo();

    Integer getVagasTotais();
}
