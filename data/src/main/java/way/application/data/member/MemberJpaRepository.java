package way.application.data.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserId(String userId);

    Optional<MemberEntity> findByEmail(String email);

    @Query("update MemberEntity set fireBaseTargetToken =:token ")
    void updateByFireBaseTargetToken(String token);
}
