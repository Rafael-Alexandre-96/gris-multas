package br.com.gris.multas.api.exception.model;

import java.io.Serializable;

public class FieldError implements Serializable {
    private static final long serialVersionUID = 1L;
        
    private String field;
    private String errorMsg;
    
    public FieldError(String field, String errorMsg) {
        this.field = field;
        this.errorMsg = errorMsg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}