package com.campusride.exception;

/**
 * Lancada quando um recurso (Carona, Reserva, ...) nao e encontrado pelo id.
 * Mapeada para HTTP 404 no GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
