package way.application.domain.member;

import jakarta.validation.constraints.NotBlank;

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
            String userId,

            @NotBlank(message = "password를 입력해주세요")
            String password,

            @NotBlank(message = "email를 입력해주세요")
            String email

    ) {

    }
}
