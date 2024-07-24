package com.demoproject.brendansia.exceptions;

import com.demoproject.brendansia.constant.Error;

public class ProductException extends RuntimeException {
    private final Error error;

    public ProductException(Error errorCode) {
        super(errorCode.getDescription());
        this.error = errorCode;
    }

    public Error getErrorCode() {
        return error;
    }
}
