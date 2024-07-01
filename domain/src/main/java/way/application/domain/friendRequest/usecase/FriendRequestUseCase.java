package way.application.domain.friendRequest.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.friendRequest.FriendRequestRepository;
import way.application.domain.friendRequest.FriendRequest;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FriendRequestUseCase {

    private final FriendRequestRepository friendRequestRepository;

    public void invoke(FriendRequest.SaveFriendRequest request) throws IOException {
        friendRequestRepository.friendRequest(request);
    }
}
