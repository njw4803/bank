package njw.project.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import njw.project.bank.domain.user.User;
import njw.project.bank.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserReqDto {

    @Setter
    @Getter
    public static class JoinReqDto {
        // 유효성 검사
        // 영문, 숫자는 되고, 길이는 최소 2~20자 이내
        @Pattern(regexp = "",message= "영문/숫자 2~20자 이내로 작성해주세요.")
        @NotEmpty // null이거나, 공백일 수 없다.
        private String username;

        // 길이 4~20
        @NotEmpty // null이거나, 공백일 수 없다.
        private String password;

        // 이메일 형식
        @NotEmpty // null이거나, 공백일 수 없다.
        private String email;

        // 영어, 한글, 1~20자 이내
        @NotEmpty // null이거나, 공백일 수 없다.
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }

}
