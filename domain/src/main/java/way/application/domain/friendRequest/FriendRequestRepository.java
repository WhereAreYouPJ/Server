package way.application.domain.friendRequest;


import java.util.List;

import static way.application.domain.friendRequest.FriendRequest.*;

public interface FriendRequestRepository {

    void friendRequest (SaveFriendRequest request);

    List<FriendRequestList> friendRequestList(Long memberSeq);
}
