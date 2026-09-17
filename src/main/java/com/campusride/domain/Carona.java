package com.campusride.domain;

import com.campusride.domain.enums.SituacaoCarona;
import com.campusride.domain.enums.TipoVeiculo;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "carona")
public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String motoristaNome;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime horarioPartida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Column(nullable = false)
    private Integer vagasTotais;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoCarona situacao;

    @OneToMany(mappedBy = "carona", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Reserva> reservas = new ArrayList<>();

    protected Carona() {
        // exigido pelo JPA
    }

    public Carona(String motoristaNome, String origem, String destino,
                  LocalDateTime horarioPartida, TipoVeiculo tipoVeiculo, Integer vagasTotais) {
        this.motoristaNome = motoristaNome;
        this.origem = origem;
        this.destino = destino;
        this.horarioPartida = horarioPartida;
        this.tipoVeiculo = tipoVeiculo;
        this.vagasTotais = vagasTotais;
        this.situacao = SituacaoCarona.ABERTA;
    }

    // --- comportamento de dominio ---

    /**
     * Numero de reservas confirmadas atualmente na carona.
     */
    public long contarReservasConfirmadas() {
        return reservas.stream()
                .filter(r -> r.getSituacao() == com.campusride.domain.enums.SituacaoReserva.CONFIRMADA)
                .count();
    }

    public long vagasDisponiveis() {
        return vagasTotais - contarReservasConfirmadas();
    }

    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public void atualizarSituacaoAposReserva() {
        if (vagasDisponiveis() <= 0) {
            this.situacao = SituacaoCarona.LOTADA;
        }
    }

    public void reabrirSeHavaVaga() {
        if (this.situacao == SituacaoCarona.LOTADA && vagasDisponiveis() > 0) {
            this.situacao = SituacaoCarona.ABERTA;
        }
    }

    public void cancelar() {
        this.situacao = SituacaoCarona.CANCELADA;
        this.reservas.forEach(Reserva::cancelar);
    }

    // --- getters / setters ---

    public Long getId() {
        return id;
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

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public void setVagasTotais(Integer vagasTotais) {
        this.vagasTotais = vagasTotais;
    }

    public SituacaoCarona getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoCarona situacao) {
        this.situacao = situacao;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}
