package com.victor.barbearia.barbearia.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleNotFound(RecursoNaoEncontradoException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(404, e.getMessage()));
    }

    @ExceptionHandler(CancelamentoNaoPermitidoException.class)
    public ResponseEntity<ApiError> handleCancelamentoNaoPermitido(CancelamentoNaoPermitidoException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ApiError(422, e.getMessage()));
    }

    @ExceptionHandler(HorarioIndisponivelException.class)
    public ResponseEntity<ApiError> handleConflict(HorarioIndisponivelException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(409, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400, message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400, e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(409, e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(409, "Não é possível excluir: existem registros vinculados a este item."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception e) {
        log.error("Erro interno não tratado: {}", e.getMessage(), e);
        String msg = "Erro interno do servidor";
        if (e.getMessage() != null) msg += ": " + e.getMessage();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(500, msg));
    }
}
