package geunbon.geunboni.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "성을 입력해 주세요.")
    private String surName;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String givenName;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;
}
