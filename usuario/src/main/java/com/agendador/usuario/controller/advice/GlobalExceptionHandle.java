package com.agendador.usuario.controller.advice;

import com.agendador.usuario.infrastructure.exceptions.ConflictException;
import com.agendador.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.agendador.usuario.infrastructure.exceptions.UnauthorizedException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Anotação requerida para ativar o nosso Controller Adivice
@ControllerAdvice
public class GlobalExceptionHandle {

    //Anotação para informar que esse metodo é uma exception global
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
