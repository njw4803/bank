package njw.project.bank.config.jwt;

/**
 * SECRET은 노출되면 안된다. (클라우드AWS - 환경변수, 파일에 있는 것을 읽을 수도 있고!!)
 * 리플래시 토큰 (X)
 */
public interface JwtVO {
    String SECRET = "메타코딩"; // HS256 (대칭키)
    int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER = "Authorization";
}
