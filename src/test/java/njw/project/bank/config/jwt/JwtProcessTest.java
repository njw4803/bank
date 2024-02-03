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

    }
}