package geunbon.geunboni.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
    HAS_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    EXPIRED_EMAIL(HttpStatus.BAD_REQUEST, "만료된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "인증되지 않은 이메일입니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "이메일을 찾을 수 없습니다."),
    ALREADY_VERIFIED_EMAIL(HttpStatus.BAD_REQUEST, "이미 인증된 이메일입니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레쉬 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 토큰 유형입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 타입입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
