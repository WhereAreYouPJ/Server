package way.application.domain.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import way.application.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;

public class Friend {

    @Getter
    @Setter
    public static class VoidSwaggerResponse {

        @Schema(example = "200")
        int status;
        @Schema(example = "SUCCESS")
        String message;
        @Schema(example = "SUCCESS")
        String data;

    }
}
