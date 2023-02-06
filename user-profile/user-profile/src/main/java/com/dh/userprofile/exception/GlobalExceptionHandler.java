package com.dh.userprofile.exception;

import com.dh.userprofile.exception.custom.BadRequestException;
import com.dh.userprofile.exception.custom.InternalServerErrorException;
import com.dh.userprofile.exception.custom.InvalidPasswordException;
import com.dh.userprofile.exception.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionPayload handleRuntimeExceptions(RuntimeException e) {
        return new ExceptionPayload(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionPayload handleBadRequestException(BadRequestException e) {
        return new ExceptionPayload(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionPayload handleInternalServerError(InternalServerErrorException e) {
        return new ExceptionPayload(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
    }

    // to do: en la issue dice bad request, chequear
    @ExceptionHandler({UserNotFoundException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionPayload handleUserNotFoundFoundException(UserNotFoundException e) {
        return new ExceptionPayload(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ExceptionHandler({InvalidPasswordException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionPayload handleInvalidPasswordException(InvalidPasswordException e) {
        return new ExceptionPayload(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
    }
}
