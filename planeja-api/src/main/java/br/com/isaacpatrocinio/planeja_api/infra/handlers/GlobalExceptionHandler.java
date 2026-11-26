package br.com.isaacpatrocinio.planeja_api.infra.handlers;

import br.com.isaacpatrocinio.planeja_api.common.exceptions.RegistroNaoEncontradoException;
import br.com.isaacpatrocinio.planeja_api.common.exceptions.ValidationException;
import br.com.isaacpatrocinio.planeja_api.common.validation.CampoInvalido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(
            ValidationException e
    ) {
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var responseMap = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", e.getCamposInvalidos()
        );

        return ResponseEntity.status(status).body(responseMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var listCamposInvalidos = e
                .getFieldErrors()
                .stream()
                .map(error -> new CampoInvalido(error.getField(), error.getDefaultMessage()))
                .toList();
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var responseMap = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "listCamposInvalidos", listCamposInvalidos
        );

        return ResponseEntity.status(status).body(responseMap);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<?> handleRegistroNaoEncontradoException(
            RegistroNaoEncontradoException e
    ) {
        var status = HttpStatus.NOT_FOUND;
        var responseMap = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "message", e.getMessage()
        );

        return ResponseEntity.status(status).body(responseMap);
    }
}