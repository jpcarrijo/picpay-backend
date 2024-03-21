package com.projects.picpaybackend.transaction;

public class UnauthorizedTransactionException extends RuntimeException {


    public UnauthorizedTransactionException(String message) {
	super(message);
    }
}
