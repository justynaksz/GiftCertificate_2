package com.epam.esm.exceptionhandler;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.InvalidInputException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Mechanism for exceptions handling.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIME_AND_DATE = "time and date";
    private static final String ERROR_MESSAGE = "error message";
    private static final String ERROR_CODE = "error code";

    /**
     * Handles InvalidInputException.
     * @param exception         thrown InvalidInputException
     * @param webRequest        actual webRequest
     * @return responseEntity   with specified body (time and date, error message and code)
     *                          and http status
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInput(InvalidInputException exception, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIME_AND_DATE, getNowAdjusted());
        body.put(ERROR_MESSAGE, exception.getMessage());
        body.put(ERROR_CODE, 40001);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AlreadyExistException.
     * @param exception         thrown AlreadyExistException
     * @param webRequest        actual webRequest
     * @return responseEntity   with specified body (time and date, error message and code)
     *                          and http status
     */
    @ExceptionHandler (AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyExistsInDb(AlreadyExistException exception, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIME_AND_DATE, getNowAdjusted());
        body.put(ERROR_MESSAGE, exception.getMessage());
        body.put(ERROR_CODE, 40002);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles EmptyResultDataAccessException.
     * @param exception         thrown EmptyResultDataAccessException
     * @param webRequest        actual webRequest
     * @return responseEntity   with specified body (time and date, error message and code)
     *                          and http status
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataNotFound(EmptyResultDataAccessException exception, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIME_AND_DATE, getNowAdjusted());
        body.put(ERROR_MESSAGE, exception.getMessage());
        body.put(ERROR_CODE, 40401);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles NotFoundException.
     * @param exception         thrown NotFoundException
     * @param webRequest        actual webRequest
     * @return responseEntity   with specified body (time and date, error message and code)
     *                          and http status
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException exception, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIME_AND_DATE, getNowAdjusted());
        body.put(ERROR_MESSAGE, exception.getMessage());
        body.put(ERROR_CODE, 40402);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    private String getNowAdjusted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss   dd-MM-yyyy");
        return LocalDateTime.now().format(formatter);
    }
}
