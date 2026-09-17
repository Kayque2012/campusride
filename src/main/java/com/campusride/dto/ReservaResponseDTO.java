package com.campusride.dto;

import com.campusride.domain.Reserva;
import com.campusride.domain.enums.SituacaoReserva;

import java.time.LocalDateTime;


public class ReservaResponseDTO {

    private Long id;
    private Long caronaId;
    private String passageiroNome;
    private LocalDateTime momentoReserva;
    private SituacaoReserva situacao;

    public ReservaResponseDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.caronaId = reserva.getCarona().getId();
        this.passageiroNome = reserva.getPassageiroNome();
        this.momentoReserva = reserva.getMomentoReserva();
        this.situacao = reserva.getSituacao();
    }

    public Long getId() {
        return id;
    }

    public Long getCaronaId() {
        return caronaId;
    }

    public String getPassageiroNome() {
        return passageiroNome;
    }

    public LocalDateTime getMomentoReserva() {
        return momentoReserva;
    }

    public SituacaoReserva getSituacao() {
        return situacao;
    }
}
