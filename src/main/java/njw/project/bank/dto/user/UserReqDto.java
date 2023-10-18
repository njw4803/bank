package njw.project.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import njw.project.bank.domain.user.User;
import njw.project.bank.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {

    @Setter
    @Getter
    public static class LoginReqDto {
        private String username;
        private String password;
    }

    @Setter
    @Getter
    public static class JoinReqDto {
        // 유효성 검사
        // 영문, 숫자는 되고, 길이는 최소 2~20자 이내
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$",message= "영문/숫자 2~20자 이내로 작성해주세요.")
        @NotEmpty // null이거나, 공백일 수 없다.
        private String username;

        // 길이 4~20
        @NotEmpty // null이거나, 공백일 수 없다.
        @Size(min = 4,max = 20) // String만 사용 가능
        private String password;

        // 이메일 형식
        @NotEmpty // null이거나, 공백일 수 없다.
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",message= "이메일 형식으로 작성해주세요")
        private String email;

        // 영어, 한글, 1~20자 이내
        @NotEmpty // null이거나, 공백일 수 없다.
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$",message= "한글/영문 1~20자 이내로 작성해주세요.")
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
