package way.application.domain.friendRequest;

import java.util.List;

public interface FriendRequestRepository {

    void friendRequest (FriendRequest.SaveFriendRequest request);

    List<FriendRequest.FriendRequestList> friendRequestList(Long memberSeq);
}
