package way.application.data.friendRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import way.application.data.member.MemberEntity;

import java.util.List;

@Repository
public interface FriendRequestJpaRepository extends JpaRepository<FriendRequestEntity, Long> {

    boolean existsByReceiverSeqAndSenderSeq(MemberEntity receiverId, MemberEntity senderId);

    List<FriendRequestEntity> findByReceiverSeq(MemberEntity memberSeq);
}
