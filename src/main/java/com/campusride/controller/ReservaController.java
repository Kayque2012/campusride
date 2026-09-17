package com.campusride.controller;

import com.campusride.domain.Reserva;
import com.campusride.dto.ReservaRequestDTO;
import com.campusride.dto.ReservaResponseDTO;
import com.campusride.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Endpoints REST do recurso Reserva.
 * A criacao de reserva fica aninhada em /caronas/{caronaId}/reservas porque
 * uma reserva sempre pertence a uma carona; consulta e cancelamento usam o
 * id proprio da reserva.
 */
@RestController
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Reserva uma vaga em uma carona.
     * POST /caronas/{caronaId}/reservas
     */
    @PostMapping("/caronas/{caronaId}/reservas")
    public ResponseEntity<ReservaResponseDTO> reservar(@PathVariable Long caronaId,
                                                         @Valid @RequestBody ReservaRequestDTO dados,
                                                         UriComponentsBuilder uriBuilder) {
        Reserva reserva = reservaService.reservar(caronaId, dados);
        URI location = uriBuilder.path("/reservas/{id}").buildAndExpand(reserva.getId()).toUri();
        return ResponseEntity.created(location).body(new ReservaResponseDTO(reserva));
    }

    /**
     * Consulta uma reserva especifica.
     * GET /reservas/{id}
     */
    @GetMapping("/reservas/{id}")
    public ResponseEntity<ReservaResponseDTO> detalhar(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(new ReservaResponseDTO(reserva));
    }

    /**
     * Cancela uma reserva.
     * DELETE /reservas/{id}
     */
    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
