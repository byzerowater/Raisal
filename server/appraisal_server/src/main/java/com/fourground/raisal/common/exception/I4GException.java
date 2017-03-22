package com.fourground.raisal.common.exception;

public interface I4GException {
	public String getErrorCode();
	public String getMessage();
	public Throwable getCause();
	public String[] getArgs();
}
