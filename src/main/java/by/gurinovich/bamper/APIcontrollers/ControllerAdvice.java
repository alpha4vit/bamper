package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.exceptions.AccessDeniedException;
import by.gurinovich.bamper.exceptions.ExceptionBody;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(final ResourceNotFoundException ex){
        return new ExceptionBody(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied(AccessDeniedException ex){
        return new ExceptionBody(ex.getMessage());
    }


}
