package way.application.data.friendRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import way.application.data.friendRequest.FriendRequestEntity;
import way.application.data.member.MemberEntity;

@Repository
public interface FriendRequestJpaRepository extends JpaRepository<FriendRequestEntity, Long> {

    boolean existsByReceiverIdAndSenderId(MemberEntity receiverId, MemberEntity senderId);
}
