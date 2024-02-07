package njw.project.bank.handler;

import njw.project.bank.dto.ResponseDto;
import njw.project.bank.handler.exception.CustomApiException;
import njw.project.bank.handler.exception.CustomForbiddenException;
import njw.project.bank.handler.exception.CustomValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class) // CustomApiException 터지면 작동
    public ResponseEntity<?> apiException(CustomApiException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomForbiddenException.class) // CustomException 터지면 작동
    public ResponseEntity<?> forbiddenException(CustomApiException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),null), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomValidationException.class) // CustomValidationException 터지면 작동
    public ResponseEntity<?> validationApiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // MethodArgumentNotValidException 터지면 작동
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("")
                ));
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),errors), HttpStatus.BAD_REQUEST);
    }


}
