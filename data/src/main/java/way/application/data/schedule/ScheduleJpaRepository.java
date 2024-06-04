package way.application.data.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
	@Query("""
		SELECT 
			se 
		FROM 
			ScheduleEntity se 
		JOIN 
			ScheduleMemberEntity sme 
			ON 
			se.scheduleSeq = sme.schedule.scheduleSeq 
		WHERE 
			sme.invitedMember.memberSeq = :memberSeq 
			AND 
			sme.acceptSchedule = true 
			AND 
			CAST(se.startTime AS date) = CAST(:requestDate AS date)
		""")
		// TODO: STARTIME 기준이 아닌 STARTTIME >= ENDTIME <= 로 쿼리 수정 필요
	List<ScheduleEntity> findAcceptedSchedulesByMemberAndDate(@Param("memberSeq") Long memberSeq,
		@Param("requestDate") LocalDateTime requestDate);
}
