package com.campusride.domain.enums;


public enum TipoVeiculo {
    MOTO(1),
    CARRO(4),
    SUV(6),
    VAN(10);

    private final int capacidadeMaximaVagas;

    TipoVeiculo(int capacidadeMaximaVagas) {
        this.capacidadeMaximaVagas = capacidadeMaximaVagas;
    }

    public int getCapacidadeMaximaVagas() {
        return capacidadeMaximaVagas;
    }
}
