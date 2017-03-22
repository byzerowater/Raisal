package com.fourground.raisal.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Unauthorized Reqeust")  // 401
public class AccessDeniedException extends BaseRuntimeException {

	private static final long serialVersionUID = -6505385700377559302L;

	/**
     * 기본 생성자
     */
    public AccessDeniedException() {
        super();
    }

    /**
     * @param errorCode the error code.
     * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public AccessDeniedException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * @param errorCode the error code.
     */
    public AccessDeniedException(String errorCode) {
        super(errorCode);
    }

    /**
     * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public AccessDeniedException(Throwable cause) {
        super(cause);
    }
}
