package way.application.data.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
    @Query("SELECT le FROM LocationEntity le WHERE le.member.id =:memberId")
    List<LocationEntity> findLocationEntityByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT ls FROM LocationEntity ls WHERE ls.member.id =:memberId AND ls.id =:locationId")
    Optional<LocationEntity> findLocationEntityByMemberIdAndLocationId(@Param("memberId") Long memberId,
                                                                       @Param("locationId") Long locationId);

    @Query("SELECT ls FROM LocationEntity ls WHERE ls.member.id =:memberId")
    List<LocationEntity> findAllLocationEntitiesByMemberId(@Param("memberId") Long memberId);
}
