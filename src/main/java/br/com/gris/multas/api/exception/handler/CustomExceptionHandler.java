package br.com.gris.multas.api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gris.multas.api.exception.EntityNotFoundException;
import br.com.gris.multas.api.exception.model.DataException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {
    
    @ResponseStatus(HttpStatus.NOT_FOUND) @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DataException> handleEntityNotFoundException(HttpServletRequest req, Exception e) {
        DataException data = new DataException();
		data.setStatus(HttpStatus.NOT_FOUND);
		data.setMessage(e.getMessage());
		return new ResponseEntity<DataException>(data, HttpStatus.NOT_FOUND);
    }
}
