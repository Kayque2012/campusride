package com.campusride.dto;

import com.campusride.domain.enums.TipoVeiculo;
import com.campusride.validation.PossuiVeiculoEVagas;
import com.campusride.validation.VagasCompativelComVeiculo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@VagasCompativelComVeiculo
public class CaronaRequestDTO implements PossuiVeiculoEVagas {

    @NotBlank(message = "motoristaNome e obrigatorio")
    private String motoristaNome;

    @NotBlank(message = "origem e obrigatoria")
    private String origem;

    @NotBlank(message = "destino e obrigatorio")
    private String destino;

    @NotNull(message = "horarioPartida e obrigatorio")
    @Future(message = "horarioPartida deve ser uma data/hora futura")
    private LocalDateTime horarioPartida;

    @NotNull(message = "tipoVeiculo e obrigatorio")
    private TipoVeiculo tipoVeiculo;

    @NotNull(message = "vagasTotais e obrigatorio")
    @Min(value = 1, message = "vagasTotais deve ser no minimo 1")
    private Integer vagasTotais;

    public CaronaRequestDTO() {
    }

    public String getMotoristaNome() {
        return motoristaNome;
    }

    public void setMotoristaNome(String motoristaNome) {
        this.motoristaNome = motoristaNome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getHorarioPartida() {
        return horarioPartida;
    }

    public void setHorarioPartida(LocalDateTime horarioPartida) {
        this.horarioPartida = horarioPartida;
    }

    @Override
    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    @Override
    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public void setVagasTotais(Integer vagasTotais) {
        this.vagasTotais = vagasTotais;
    }
}
