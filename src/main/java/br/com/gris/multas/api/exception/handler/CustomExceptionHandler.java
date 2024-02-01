package br.com.gris.multas.api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gris.multas.api.exception.CustomConstraintViolationException;
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

    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) @ExceptionHandler(RuntimeException.class)
	public ResponseEntity<DataException> handleRuntimeException(HttpServletRequest req, Exception e) {
		DataException data = new DataException();
		data.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		data.setMessage(e.getMessage());
		return new ResponseEntity<DataException>(data, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/

	@ResponseStatus(HttpStatus.BAD_REQUEST) @ExceptionHandler(CustomConstraintViolationException.class)
	public ResponseEntity<DataException> handleConstraintViolationException(HttpServletRequest req, CustomConstraintViolationException e) {
		DataException data = new DataException();
		data.setStatus(HttpStatus.BAD_REQUEST);
		data.setMessage("Um ou mais campos estão inválidos.");
		data.setFieldErros(e.getFieldErros());
		return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
	}
}
