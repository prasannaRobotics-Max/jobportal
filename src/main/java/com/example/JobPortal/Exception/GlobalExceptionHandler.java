package com.example.JobPortal.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmailNotFoundException(EmailNotFoundException e, WebRequest webRequest){
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(),webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e,WebRequest webRequest)
    {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(), webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCompanyNotFoundException(CompanyNotFoundException e,WebRequest webRequest)
    {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(), webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CompanyLocationNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCompanyLocationNotFoundException(CompanyLocationNotFoundException e,WebRequest webRequest)
    {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(), webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JobRoleNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleJobRoleNotFoundException(JobRoleNotFoundException e,WebRequest webRequest)
    {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(), webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SalaryNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleSalaryNotFoundException(SalaryNotFoundException e,WebRequest webRequest)
    {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(), webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatchingJobsNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleMatchingJobsNotFoundException(MatchingJobsNotFoundException e,WebRequest webRequest)
    {
        ErrorDetails error=new ErrorDetails(
                LocalDateTime.now(),e.getMessage(), webRequest.getDescription(false),e.getResourceCode());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception e,
                                                              WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                e.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {

        return new ResponseEntity<>(
                Map.of("error", "Access Denied. You are not authorized to access this resource."),
                HttpStatus.FORBIDDEN
        );
    }
}
