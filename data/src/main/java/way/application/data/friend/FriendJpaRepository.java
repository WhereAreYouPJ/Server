package way.application.data.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import way.application.data.member.MemberEntity;
import way.application.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface FriendJpaRepository extends JpaRepository<FriendEntity, Long> {

    List<FriendEntity> findByOwner(MemberEntity owner);

    Optional<FriendEntity> findByOwnerAndFriends(MemberEntity owner, MemberEntity friends);

}
