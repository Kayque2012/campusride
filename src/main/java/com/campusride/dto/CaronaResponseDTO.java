package com.campusride.dto;

import com.campusride.domain.Carona;
import com.campusride.domain.enums.SituacaoCarona;
import com.campusride.domain.enums.TipoVeiculo;

import java.time.LocalDateTime;
import java.util.List;


public class CaronaResponseDTO {

    private Long id;
    private String motoristaNome;
    private String origem;
    private String destino;
    private LocalDateTime horarioPartida;
    private TipoVeiculo tipoVeiculo;
    private Integer vagasTotais;
    private long vagasDisponiveis;
    private SituacaoCarona situacao;
    private List<ReservaResponseDTO> reservas;

    public static CaronaResponseDTO resumo(Carona carona) {
        CaronaResponseDTO dto = base(carona);
        dto.reservas = null;
        return dto;
    }

    public static CaronaResponseDTO detalhado(Carona carona) {
        CaronaResponseDTO dto = base(carona);
        dto.reservas = carona.getReservas().stream()
                .map(ReservaResponseDTO::new)
                .toList();
        return dto;
    }

    private static CaronaResponseDTO base(Carona carona) {
        CaronaResponseDTO dto = new CaronaResponseDTO();
        dto.id = carona.getId();
        dto.motoristaNome = carona.getMotoristaNome();
        dto.origem = carona.getOrigem();
        dto.destino = carona.getDestino();
        dto.horarioPartida = carona.getHorarioPartida();
        dto.tipoVeiculo = carona.getTipoVeiculo();
        dto.vagasTotais = carona.getVagasTotais();
        dto.vagasDisponiveis = carona.vagasDisponiveis();
        dto.situacao = carona.getSituacao();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getMotoristaNome() {
        return motoristaNome;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getHorarioPartida() {
        return horarioPartida;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public long getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public SituacaoCarona getSituacao() {
        return situacao;
    }

    public List<ReservaResponseDTO> getReservas() {
        return reservas;
    }
}
