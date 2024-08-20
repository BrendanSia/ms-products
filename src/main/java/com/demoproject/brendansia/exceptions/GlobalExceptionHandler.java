package com.demoproject.brendansia.exceptions;

import com.demoproject.brendansia.constant.Error;
import com.demoproject.brendansia.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String failStatus = "Fail";

    @ExceptionHandler(ProductException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(ProductException ex) {
        Error error = ex.getErrorCode();
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(failStatus, error.getCode(), error.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}
