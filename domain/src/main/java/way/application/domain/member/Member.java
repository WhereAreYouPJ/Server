package way.application.domain.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Member {
    public record Request(
            @NotBlank(message = "값을 입력해주세요")
            String userId,

            @NotBlank(message = "값을 입력해주세요")
            String password
    ) {

    }

    public record SaveMemberRequest(
            @NotBlank(message = "userName을 입력해주세요")
            String userName,

            @NotBlank(message = "userId를 입력해주세요")
            @Pattern(regexp = "^[a-z][a-z0-9]{4,11}$", message = "userId는 영문 소문자로 시작하고, 5~12자 길이의 영문 소문자와 숫자만 사용 가능합니다.")
            String userId,

            @NotBlank(message = "password를 입력해주세요")
            @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{5,19}$", message = "password는 대문자 혹은 소문자로 시작하고, 6~20자 길이의 영문 대문자, 소문자, 숫자만 사용 가능합니다.")
            String password,

            @NotBlank(message = "email를 입력해주세요")
            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "유효한 이메일 형식이어야 합니다.")
            String email
    ) {

    }

    public record CheckIdRequest(
            @NotBlank(message = "userId를 입력해주세요")
            @Pattern(regexp = "^[a-z][a-z0-9]{4,11}$", message = "userId는 영문 소문자로 시작하고, 5~12자 길이의 영문 소문자와 숫자만 사용 가능합니다.")
            String userId
    ) {

    }

    public record CheckIdResponse (
            String userId
    ) {

    }

    public record CheckEmailRequest(
            @NotBlank(message = "email를 입력해주세요")
            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "유효한 이메일 형식이어야 합니다.")
            String email
    ) {

    }

    public record CheckEmailResponse(
            String email
    ) {

    }

    public record MemberLoginRequest(
            @NotBlank(message = "userId를 입력해주세요")
            @Pattern(regexp = "^[a-z][a-z0-9]{4,11}$", message = "userId는 영문 소문자로 시작하고, 5~12자 길이의 영문 소문자와 숫자만 사용 가능합니다.")
            String userId,

            @NotBlank(message = "password를 입력해주세요")
            @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{5,19}$", message = "password는 대문자 혹은 소문자로 시작하고, 6~20자 길이의 영문 대문자, 소문자, 숫자만 사용 가능합니다.")
            String password,

            String targetToken
    ) {

    }

    public record MemberLoginResponse(
            String accessToken,
            String refreshToken,
            Long id

    ) {

    }

    public record MailSendRequest(
            @NotBlank(message = "email를 입력해주세요")
            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "유효한 이메일 형식이어야 합니다.")
            String email
    ) {

    }

    public record CodeVerifyRequest(

            @NotBlank(message = "email를 입력해주세요")
            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "유효한 이메일 형식이어야 합니다.")
            String email,

            String code

    ) {

    }


}
