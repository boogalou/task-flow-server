package su.tomcat.taskflow.web.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import su.tomcat.taskflow.domain.exception.AccessDeniedException;
import su.tomcat.taskflow.domain.exception.ExceptionBody;
import su.tomcat.taskflow.domain.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionBody handleResourceNotFound(ResourceNotFoundException err) {
    return new ExceptionBody(err.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleIllegalState(IllegalStateException err) {
    return new ExceptionBody(err.getMessage());
  }

  @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ExceptionBody handleAccessDenied() {
    return new ExceptionBody("Access denied");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException err) {
    ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
    List<FieldError> errors = err.getBindingResult().getFieldErrors();
    exceptionBody.setErrors(errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
    return exceptionBody;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleConstraintViolation(ConstraintViolationException err) {
    ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
    exceptionBody.setErrors(err.getConstraintViolations().stream().collect(Collectors.toMap(
        violation -> violation.getPropertyPath().toString(),
        ConstraintViolation::getMessage
    )));

    return exceptionBody;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionBody handleException(Exception err) {
    return new ExceptionBody("Internal server error");
  }

}
