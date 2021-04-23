package com.example.alfred.exception;

import org.springframework.http.HttpStatus;

public abstract class AspectException extends Exception {

	private static final long serialVersionUID = 1L;
	protected HttpStatus status=HttpStatus.INTERNAL_SERVER_ERROR;

	protected AspectException(String message) {
		super(message);
		setStatus();
	}

	protected abstract void setStatus();

	protected HttpStatus getExceptionStatus() {
		return status;
	}
}
