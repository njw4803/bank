package njw.project.bank.service;

import njw.project.bank.config.dummy.DummyObject;
import njw.project.bank.domain.user.User;
import njw.project.bank.domain.user.UserRepository;
import njw.project.bank.dto.user.UserReqDto.JoinReqDto;
import njw.project.bank.dto.user.UserRespDto.JoinRespDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Spring 관련 Bean들이 하나도 없는 환경
public class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_test() throws Exception {
        // given

        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("njw");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("njw@naver.com");
        joinReqDto.setFullname("노지원");

        // stub
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        // stub2
        User user = newMockUser(1L,"njw","노지원");
        when(userRepository.save(any())).thenReturn(user);

        // when
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        System.out.println("테스트 = " + joinRespDto);


        // then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("njw");
    }
}
