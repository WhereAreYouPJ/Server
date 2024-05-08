package way.application.data.scheduleMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import way.application.data.schedule.ScheduleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleMemberJpaRepository extends JpaRepository<ScheduleMemberEntity, Long> {
    void deleteAllBySchedule(ScheduleEntity scheduleEntity);

    @Query("SELECT sme FROM ScheduleMemberEntity sme WHERE sme.schedule =:scheduleEntity AND sme.acceptSchedule = true")
    List<ScheduleMemberEntity> findAcceptedScheduleMemberByScheduleEntity(@Param("scheduleEntity") ScheduleEntity scheduleEntity);

    @Query("SELECT sme FROM ScheduleMemberEntity sme WHERE sme.schedule.id =:scheduleId AND sme.invitedMember.id =:memberId AND sme.acceptSchedule = true")
    Optional<ScheduleMemberEntity> findAcceptedScheduleMemberByScheduleIdAndMemberId(@Param("scheduleId") Long scheduleId,
                                                                                     @Param("memberId") Long memberId);
}
