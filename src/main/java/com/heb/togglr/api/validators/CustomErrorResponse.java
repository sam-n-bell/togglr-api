package com.heb.togglr.api.validators;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

public class CustomErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public CustomErrorResponse() {

    }

    public CustomErrorResponse(HttpStatus status, List<String> errors) {
        this.status = status;
        this.errors = errors;
        StringBuilder sb = new StringBuilder();
        for (String error: errors) {
            sb.append(error);
            sb.append(". ");
        }
        this.message = sb.toString().trim();
    }

    public HttpStatus getStatus() {
        return status;
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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
