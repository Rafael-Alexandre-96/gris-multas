package br.com.gris.multas.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

import br.com.gris.multas.api.exception.model.FieldError;

public class CustomConstraintViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private List<FieldError> fieldErros = new ArrayList<>();

	public CustomConstraintViolationException() {
		super();
	}
    
	public CustomConstraintViolationException(String message) {
		super(message);
	}

    public List<FieldError> getFieldErros() {
		return fieldErros;
	}

	public void setFieldErros(List<FieldError> fieldsErros) {
		this.fieldErros = fieldsErros;
	}

    public void addFieldError(@NonNull String field, @NonNull String errorMsg) {
        FieldError fieldError = new FieldError(field, errorMsg);
        this.fieldErros.add(fieldError);
    }
}