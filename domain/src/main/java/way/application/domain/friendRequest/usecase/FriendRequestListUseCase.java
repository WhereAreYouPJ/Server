package way.application.domain.friendRequest.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.friendRequest.FriendRequestRepository;

import java.util.List;

import static way.application.domain.friendRequest.FriendRequest.*;

@Component
@RequiredArgsConstructor
public class FriendRequestListUseCase {

    private final FriendRequestRepository friendRequestRepository;

    public List<FriendRequestList> invoke(Long memberSeq) {
        return friendRequestRepository.friendRequestList(memberSeq);
    }

}
