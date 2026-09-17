package com.campusride.service;

import com.campusride.domain.Carona;
import com.campusride.domain.Reserva;
import com.campusride.domain.enums.SituacaoCarona;
import com.campusride.domain.enums.SituacaoReserva;
import com.campusride.dto.ReservaRequestDTO;
import com.campusride.exception.BusinessException;
import com.campusride.exception.ResourceNotFoundException;
import com.campusride.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CaronaService caronaService;

    public ReservaService(ReservaRepository reservaRepository, CaronaService caronaService) {
        this.reservaRepository = reservaRepository;
        this.caronaService = caronaService;
    }

    @Transactional
    public Reserva reservar(Long caronaId, ReservaRequestDTO dados) {
        Carona carona = caronaService.buscarPorId(caronaId);

        if (carona.getSituacao() == SituacaoCarona.CANCELADA
                || carona.getSituacao() == SituacaoCarona.CONCLUIDA
                || carona.getSituacao() == SituacaoCarona.EM_ANDAMENTO) {
            throw new BusinessException(
                    "Nao e possivel reservar vaga em uma carona " + carona.getSituacao().name().toLowerCase());
        }

        if (carona.vagasDisponiveis() <= 0) {
            throw new BusinessException("Carona sem vagas disponiveis");
        }

        Reserva reserva = new Reserva(carona, dados.getPassageiroNome());
        carona.adicionarReserva(reserva);
        reserva = reservaRepository.save(reserva);

        carona.atualizarSituacaoAposReserva();

        return reserva;
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva nao encontrada: id " + id));
    }

    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = buscarPorId(id);
        Carona carona = reserva.getCarona();

        if (carona.getSituacao() == SituacaoCarona.CONCLUIDA) {
            throw new BusinessException("Nao e possivel cancelar reserva de uma carona ja concluida");
        }
        if (reserva.getSituacao() == SituacaoReserva.CANCELADA) {
            throw new BusinessException("Reserva ja esta cancelada");
        }

        reserva.cancelar();
        carona.reabrirSeHavaVaga();
    }
}
