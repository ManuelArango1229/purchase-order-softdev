package com.softdev.purchase_order.infrastucture.rest;

import com.softdev.purchase_order.use_cases.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y maneja excepciones específicas y genera respuestas adecuadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo IllegalArgumentException.
     *
     * @param ex La excepción capturada.
     * @return Una respuesta con el estado HTTP 400 y un mensaje de error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Entrada inválida", ex.getMessage()));
    }

    /**
     * Maneja excepciones de tipo RuntimeException.
     *
     * @param ex La excepción capturada.
     * @return Una respuesta con el estado HTTP 500 y un mensaje de error.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(final RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno", ex.getMessage()));
    }

    /**
     * Maneja excepciones generales.
     *
     * @param ex La excepción capturada.
     * @return Una respuesta con el estado HTTP 500 y un mensaje de error genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error inesperado", ex.getMessage()));
    }
}
