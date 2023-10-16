package njw.project.bank.handler;

import njw.project.bank.dto.ResponseDto;
import njw.project.bank.handler.exception.CustomException;
import njw.project.bank.handler.exception.CustomValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomException.class) // CustomException이 터지면 작동
    public ResponseEntity<?> apiException(CustomException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class) // CustomValidationException이 터지면 작동
    public ResponseEntity<?> validationApiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}
