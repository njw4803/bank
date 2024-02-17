package njw.project.bank.web;

import lombok.RequiredArgsConstructor;
import njw.project.bank.dto.ResponseDto;
import njw.project.bank.dto.user.UserReqDto.JoinReqDto;
import njw.project.bank.dto.user.UserRespDto;
import njw.project.bank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {//유효성 검사에 통과하지 못하면 BindingResult에 모든 오류가 담긴다.

        UserRespDto.JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원가입 완료",joinRespDto), HttpStatus.CREATED);
    }

}
