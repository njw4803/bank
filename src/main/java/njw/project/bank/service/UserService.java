package njw.project.bank.service;

import lombok.RequiredArgsConstructor;
import njw.project.bank.domain.user.User;
import njw.project.bank.domain.user.UserRepository;
import njw.project.bank.dto.user.UserReqDto.JoinReqDto;
import njw.project.bank.dto.user.UserRespDto.JoinRespDto;
import njw.project.bank.handler.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 서비스는 DTO를 요청받고, DTO로 응답한다.
    @Transactional // 트랙잭션이 메서드 시작할 때, 시작되고, 종료될 때 함께 종료
    public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            // 유저네임 중복되었다는 뜻
            throw new CustomException("동일한 username이 존재합니다.");
        }
        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new JoinRespDto(userPS);
    }




}
