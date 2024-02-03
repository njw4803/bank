package njw.project.bank.config.jwt;

import njw.project.bank.config.auth.LoginUser;
import njw.project.bank.domain.user.User;
import njw.project.bank.domain.user.UserEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    @Test
    public void create_test() throws Exception {

        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 = " + jwtToken);

        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_tset() throws Exception {
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5rIiwicm9sZSI6IkNVU1RPTUVSIiwiaWQiOjEsImV4cCI6MTcwNzU3NDI3M30.9pxcdVT-OSSA1OB-xaNUsmjm7ZW2Fz5LW1KUhZrPSlR1tnqTLTStT9DxWi8Pl9z5b76POPLuvVE6aXcL7gdmKQ";
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("loginUser.getUser().getId() = " + loginUser.getUser().getId());
    }
}