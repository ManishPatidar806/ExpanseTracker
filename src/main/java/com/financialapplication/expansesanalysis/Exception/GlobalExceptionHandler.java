package com.financialapplication.expansesanalysis.Exception;


import com.financialapplication.expansesanalysis.Model.Response.ErrorResponse;
import com.financialapplication.expansesanalysis.Model.Response.ValidateErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(getClass().getName());

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        if(exception.getMessage().isEmpty()){
            errorResponse.setMessage("Resource Not Found");
        }else{
        errorResponse.setMessage(exception.getMessage());
        }
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("User Already Exist.");
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("The requested file could not be found on the server.");
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLExceptionException(SQLException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("A database error occurred while processing the request.");
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidateErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError err : exception.getBindingResult().getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }
        ValidateErrorResponse response = new ValidateErrorResponse();
        response.setMessage(errors);
        response.setStatus(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnAuthorizeException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(UnAuthorizeException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Unauthorized access detected. Please ensure you have the necessary permissions to perform this action.");
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        logger.warning(exception.getMessage());
        errorResponse.setMessage("An unexpected error occurred while processing your request. Please try again later or contact support if the issue persists.");
        errorResponse.setStatus(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}