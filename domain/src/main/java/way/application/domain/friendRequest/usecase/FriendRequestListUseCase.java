package way.application.domain.friendRequest.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.friendRequest.FriendRequest;
import way.application.domain.friendRequest.FriendRequestRepository;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRequestListUseCase {

    private final FriendRequestRepository friendRequestRepository;

    public List<FriendRequest.FriendRequestList> invoke(Long memberSeq) {
        return friendRequestRepository.friendRequestList(memberSeq);
    }

}
