package njw.project.bank.handler.error;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.Objects;

public class Error {

    @Getter
    @RequiredArgsConstructor
    public enum ErrorEnum {
        NOT_NULL("Null 값을 허용하지 않습니다."),
        NOT_EMPTY("를(을) 입력해주세요."),
        NOT_BLANK("공백과 빈 값은 넣을 수 없습니다."),
        EMAIL("Email 형식에 맞지 않습니다.");

        private final String value;
    }

    public static class ErrorMessage {
        public static String getDefaultErrorMessage(FieldError fieldError) {
            return fieldError.getDefaultMessage();
        }

        public static String getErrorMessage(FieldError fieldError) {

            String message = "";
            switch (Objects.requireNonNull(fieldError.getCode())) {
                case "NotNull" -> message =  fieldError.getDefaultMessage() + "은(는) " + ErrorEnum.NOT_NULL.value;
                case "NotEmpty" -> message = fieldError.getDefaultMessage() + "은(는) " + ErrorEnum.NOT_EMPTY.value;
                case "NotBlank" -> message = fieldError.getDefaultMessage() + "은(는) " + ErrorEnum.NOT_BLANK.value;
                case "Email" -> message = fieldError.getDefaultMessage() + "은(는) " + ErrorEnum.EMAIL.value;
                case "Pattern" -> message = fieldError.getDefaultMessage();
            }
            return message;
        }


    }
}
