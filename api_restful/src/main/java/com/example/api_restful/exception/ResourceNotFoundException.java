package com.example.api_restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso específico não é encontrado no sistema.
 * A anotação @ResponseStatus faz com que o Spring retorne o código HTTP 404 Not Found
 * sempre que esta exceção é lançada por um controller e não é tratada de outra forma.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
