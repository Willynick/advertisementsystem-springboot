package com.senlainc.advertisementsystem.exceptionhandling;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.validatorimpl.exception.ExistingEntityException;
import com.senlainc.advertisementsystem.validatorimpl.exception.NotFoundException;
import com.senlainc.advertisementsystem.validatorimpl.exception.ServiceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                Constants.ILLEGAL_ARGUMENT,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                Constants.VALIDATION_ERROR,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                Constants.TYPE_MISMATCH,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                Constants.CONSTRAINT_VIOLATIONS,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getParameterName() + " parameter is missing");

        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                Constants.MISSING_PARAMETERS,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @Override
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                Constants.UNSUPPORTED_MEDIA_TYPE,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

        ErrorResponse err = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                Constants.METHOD_NOT_FOUND,
                details);

        return ResponseEntityBuilder.build(err);

    }

    @ExceptionHandler(ExistingEntityException.class)
    public ResponseEntity<Object> handleExistingEntityException(ExistingEntityException ex) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.CONFLICT,
                Constants.RESOURCE_NOT_FOUND,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                Constants.RESOURCE_NOT_FOUND,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleServiceException(DatabaseException ex) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException ex) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.CONFLICT,
                Constants.RESOURCE_NOT_FOUND,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                Constants.ERROR_OCCURRED,
                details);

        return ResponseEntityBuilder.build(err);
    }
}
