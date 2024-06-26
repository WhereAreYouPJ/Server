package way.application.domain.friend;

import java.time.LocalDateTime;

public class Friend {

    public record FriendRequest(

            // 친추 보낼 아이디
            Long memberSeq,
            // 친추 보낸 아이디
            Long friendSeq,

            LocalDateTime localDateTime

    ) {

    }
}
