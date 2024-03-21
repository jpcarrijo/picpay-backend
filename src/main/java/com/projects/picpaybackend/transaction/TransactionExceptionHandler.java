package com.projects.picpaybackend.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {


    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Object> handler(InvalidTransactionException exception) {
	return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
