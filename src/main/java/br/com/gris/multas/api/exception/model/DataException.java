package br.com.gris.multas.api.exception.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DataException implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	private String message;
	private LocalDateTime dateTime = LocalDateTime.now();
	private List<FieldError> fieldErros;
	
	public int getStatus() {
		return status.value();
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public List<FieldError> getFieldErros() {
		return fieldErros;
	}

	public void setFieldErros(List<FieldError> fieldsErros) {
		this.fieldErros = fieldsErros;
	}
    
}
