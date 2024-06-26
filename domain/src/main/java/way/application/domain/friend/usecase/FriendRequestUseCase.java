package way.application.domain.friend.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.friend.Friend;
import way.application.domain.friend.FriendRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class FriendRequestUseCase {

    private final FriendRepository friendRepository;

    public void invoke(Friend.FriendRequest request) throws IOException {
        friendRepository.friendRequest(request);
    }
}
