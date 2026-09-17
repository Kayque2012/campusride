package com.campusride.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        // erros de validacao a nivel de classe (ex: @VagasCompativelComVeiculo) nao tem "field"
        List<String> globais = ex.getBindingResult().getGlobalErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .toList();

        List<String> todosDetalhes = new java.util.ArrayList<>();
        todosDetalhes.addAll(detalhes);
        todosDetalhes.addAll(globais);

        ApiError erro = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Dados invalidos",
                "Um ou mais campos nao passaram na validacao",
                request.getRequestURI(),
                todosDetalhes
        );
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNaoEncontrado(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Recurso nao encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleRegraDeNegocio(BusinessException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Regra de negocio violada",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenerico(Exception ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
