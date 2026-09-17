package com.campusride.controller;

import com.campusride.domain.Carona;
import com.campusride.dto.CaronaRequestDTO;
import com.campusride.dto.CaronaResponseDTO;
import com.campusride.service.CaronaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/caronas")
public class CaronaController {

    private final CaronaService caronaService;

    public CaronaController(CaronaService caronaService) {
        this.caronaService = caronaService;
    }


    @PostMapping
    public ResponseEntity<CaronaResponseDTO> publicar(@Valid @RequestBody CaronaRequestDTO dados,
                                                        UriComponentsBuilder uriBuilder) {
        Carona carona = caronaService.publicar(dados);
        URI location = uriBuilder.path("/caronas/{id}").buildAndExpand(carona.getId()).toUri();
        return ResponseEntity.created(location).body(CaronaResponseDTO.resumo(carona));
    }

    
    @GetMapping
    public ResponseEntity<List<CaronaResponseDTO>> listar(
            @RequestParam(name = "todas", defaultValue = "false") boolean todas) {
        List<Carona> caronas = todas ? caronaService.listarTodas() : caronaService.listarDisponiveis();
        List<CaronaResponseDTO> resposta = caronas.stream()
                .map(CaronaResponseDTO::resumo)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<CaronaResponseDTO> detalhar(@PathVariable Long id) {
        Carona carona = caronaService.buscarPorId(id);
        return ResponseEntity.ok(CaronaResponseDTO.detalhado(carona));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        caronaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
