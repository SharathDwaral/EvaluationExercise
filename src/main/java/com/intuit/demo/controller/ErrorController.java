package com.intuit.demo.controller;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.intuit.demo.domain.Error;
import com.intuit.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sdwaral on 8/27/17.
 *
 * This classes handles errors thrown by application
 */

@ControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MismatchedInputException.class)
    public @ResponseBody
    Error inputException (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.BAD_REQUEST.value());
        err.setErrorType(HttpStatus.BAD_REQUEST.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public @ResponseBody
    Error methodNotAllowed (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        err.setErrorType(HttpStatus.METHOD_NOT_ALLOWED.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PayloadValidationError.class)
    public @ResponseBody
    Error payloadValidation (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.BAD_REQUEST.value());
        err.setErrorType(HttpStatus.BAD_REQUEST.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerNotFound.class)
    public @ResponseBody
    Error custometNotFound (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.BAD_REQUEST.value());
        err.setErrorType(HttpStatus.BAD_REQUEST.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MerchantNotFound.class)
    public @ResponseBody
    Error merchantNotFound (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.NOT_FOUND.value());
        err.setErrorType(HttpStatus.NOT_FOUND.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailNotFound.class)
    public @ResponseBody
    Error emailNotFound (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.NOT_FOUND.value());
        err.setErrorType(HttpStatus.NOT_FOUND.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoNameFound.class)
    public @ResponseBody
    Error noNameFound (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.NOT_FOUND.value());
        err.setErrorType(HttpStatus.NOT_FOUND.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidHeaderError.class)
    public @ResponseBody
    Error invalidHeader (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.UNAUTHORIZED.value());
        err.setErrorType(HttpStatus.UNAUTHORIZED.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public @ResponseBody
    Error invalidQueryParams (HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.BAD_REQUEST.value());
        err.setErrorType(HttpStatus.BAD_REQUEST.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Error getAllCustomers(HttpServletRequest req, Exception ex) {

        Error err = new Error();
        err.setErrMsg(ex.getMessage());
        err.setErrStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setErrorType(HttpStatus.INTERNAL_SERVER_ERROR.name());
        err.setRequestPath(req.getRequestURL().toString());

        return err;

    }
}
