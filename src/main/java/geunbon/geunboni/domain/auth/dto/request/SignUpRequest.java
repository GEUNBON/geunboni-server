package geunbon.geunboni.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "성을 입력해 주세요.")
    private String surName;

    @Size(min = 2, max = 5, message = "이름은 2자 이상 5자 이하여야 합니다.")
    @NotBlank(message = "이름을 입력해 주세요.")
    private String givenName;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "비밀번호는 영어와 숫자를 포함한 8자에서 20자 사이여야 합니다.")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;
}
