package com.campusride.service;

import com.campusride.domain.Carona;
import com.campusride.domain.enums.SituacaoCarona;
import com.campusride.dto.CaronaRequestDTO;
import com.campusride.exception.BusinessException;
import com.campusride.exception.ResourceNotFoundException;
import com.campusride.repository.CaronaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CaronaService {

    private final CaronaRepository caronaRepository;

    public CaronaService(CaronaRepository caronaRepository) {
        this.caronaRepository = caronaRepository;
    }

    @Transactional
    public Carona publicar(CaronaRequestDTO dados) {
        Carona carona = new Carona(
                dados.getMotoristaNome(),
                dados.getOrigem(),
                dados.getDestino(),
                dados.getHorarioPartida(),
                dados.getTipoVeiculo(),
                dados.getVagasTotais()
        );
        return caronaRepository.save(carona);
    }

    @Transactional(readOnly = true)
    public List<Carona> listarDisponiveis() {
        return caronaRepository.findBySituacao(SituacaoCarona.ABERTA);
    }

    @Transactional(readOnly = true)
    public List<Carona> listarTodas() {
        return caronaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Carona buscarPorId(Long id) {
        return caronaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carona nao encontrada: id " + id));
    }

    @Transactional
    public void cancelar(Long id) {
        Carona carona = buscarPorId(id);

        if (carona.getSituacao() == SituacaoCarona.CONCLUIDA) {
            throw new BusinessException("Nao e possivel cancelar uma carona ja concluida");
        }
        if (carona.getSituacao() == SituacaoCarona.CANCELADA) {
            throw new BusinessException("Carona ja esta cancelada");
        }

        carona.cancelar();
        caronaRepository.save(carona);
    }
}
