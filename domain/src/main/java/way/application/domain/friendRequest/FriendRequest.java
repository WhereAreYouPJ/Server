package way.application.domain.friendRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class FriendRequest {

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

    public record SaveFriendRequest(

            // 친추 보낼 아이디
            Long memberSeq,
            // 친추 보낸 아이디
            Long friendSeq,
            LocalDateTime localDateTime

    ) {

    }

    public record FriendRequestList(

            Long friendRequestSeq,
            Long senderId,
            LocalDateTime createTime
    ) {

    }

}
