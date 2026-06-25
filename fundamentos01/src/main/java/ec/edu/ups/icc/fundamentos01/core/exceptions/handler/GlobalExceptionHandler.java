package ec.edu.ups.icc.fundamentos01.core.exceptions.handler;

import ec.edu.ups.icc.fundamentos01.core.exceptions.base.ApplicationException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException exception,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                exception.getStatus(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Datos de entrada invalidos",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
