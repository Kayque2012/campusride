package com.campusride.domain;

import com.campusride.domain.enums.SituacaoReserva;
import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carona_id", nullable = false)
    private Carona carona;

    @Column(nullable = false)
    private String passageiroNome;

    @Column(nullable = false)
    private LocalDateTime momentoReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoReserva situacao;

    protected Reserva() {
        // exigido pelo JPA
    }

    public Reserva(Carona carona, String passageiroNome) {
        this.carona = carona;
        this.passageiroNome = passageiroNome;
        this.momentoReserva = LocalDateTime.now();
        this.situacao = SituacaoReserva.CONFIRMADA;
    }

    public void cancelar() {
        this.situacao = SituacaoReserva.CANCELADA;
    }

    // --- getters ---

    public Long getId() {
        return id;
    }

    public Carona getCarona() {
        return carona;
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
