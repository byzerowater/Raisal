package com.fourground.raisal.common.exception;

public class BaseRuntimeException extends RuntimeException implements I4GException {
	
	private static final long serialVersionUID = 5108067886888397782L;

	private String errorCode;
	private String[] args;
	private Throwable cause;

	@Override
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	@Override
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public Throwable getCause() {
		return cause;
	}
	public void setCause(Throwable cause) {
		this.cause = cause;
	}


	/**
	 *
	 */

	/**
	 * 기본 생성자
	 */
	public BaseRuntimeException() {
		super();
	}

	/**
	 * @param errorCode the error code.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public BaseRuntimeException(String errorCode,String[] args, Throwable cause) {
		super(cause.getMessage(), cause);
		this.errorCode = errorCode;
		this.args = args;
		this.cause = cause;
	}
	public BaseRuntimeException(String errorCode, Throwable cause) {
		super(cause.getMessage(), cause);
		this.errorCode = errorCode;
		this.cause = cause;
	}

	/**
	 * @param errorCode the error code.
	 */
	public BaseRuntimeException(String errorCode,String[] args) {
		super(errorCode);
		this.errorCode = errorCode;
		this.args = args;
	}
	public BaseRuntimeException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	/**
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
		this.cause = cause;
		this.errorCode = cause.getMessage();
	}
}
