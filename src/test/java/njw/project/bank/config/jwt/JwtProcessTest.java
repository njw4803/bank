package njw.project.bank.config.jwt;

import njw.project.bank.config.auth.LoginUser;
import njw.project.bank.domain.user.User;
import njw.project.bank.domain.user.UserEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    @Test
    public void create_test() throws Exception {

        User user = User.builder().id(1L).role(UserEnum.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);
        
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 = " + jwtToken);

        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws Exception {
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5rIiwicm9sZSI6IkFETUlOIiwiaWQiOjEsImV4cCI6MTcwNzU3NDk4OH0.CvXNhby7V78y_YP1tUlEZ-n6bcRNmsBqD25IfHC9pMJMdBxXP8Q4Vfr0k6oGF9zUt2IV9xwkYNeTAKEYqV14lQ";

        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("loginUser.getUser().getId() = " + loginUser.getUser().getId());
        System.out.println("loginUser.getUser().getRole().name() = " + loginUser.getUser().getRole().name());

        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getRole()).isEqualTo(UserEnum.ADMIN);
    }
}