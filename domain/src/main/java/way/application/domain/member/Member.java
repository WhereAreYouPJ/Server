package way.application.domain.member;

import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

public class Member {
    public record Request(
            @NotBlank(message = "값을 입력해주세요")
            String userId,

            @NotBlank(message = "값을 입력해주세요")
            String password
    ) {

    }
}
