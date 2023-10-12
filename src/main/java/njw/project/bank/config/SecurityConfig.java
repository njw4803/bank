package njw.project.bank.config;

import njw.project.bank.domain.user.UserEnum;
import njw.project.bank.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean // Ioc 컨테이너에 BCryptPasswordEncoder() 등록됨.
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함

    // JWT 서버를 사용. Session 사용안함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); // iframe 허용안함
        http.csrf().disable(); // enable이면 post맨 작동안함
        http.cors().configurationSource(configurationSource());

        // jSessionId를 서버쪽에서 관리안하겠다는 뜻
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // react, 앱으로 요청할 예정
        http.formLogin().disable();
        // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
        http.httpBasic().disable();

        // Exception 가로채기
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.unAuthentication(response,"로그인을 진행해 주세요.");
        });

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(String.valueOf(UserEnum.ADMIN)) // 최근 공식문서는 ROLE_ 생략
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (실무에서는 모든 IP 주소 허용보다는 프론트엔드 IP만 허용. react). 핸드폰 앱 IP는 모든 사용자가 다 다른 IP를 쓰지만 자바언어나 스위프트 언어 등으로 요청하기때문에 CORS정책에 걸리지가 않는다.
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용. 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
                                                 // config.setAllowCredentials(true); true로 설정하면 ajax,fetch 등 에서 요청하면 그 응답을 자바스크립트가 받을 수 있게 한다.
                                                 // false로 하면 내 서버가 결정하는데 자바스크립트가 요청을 할 때 응답이 오지않는다.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 주소는 이 config 설정을 따름

        return source;
    }
}
