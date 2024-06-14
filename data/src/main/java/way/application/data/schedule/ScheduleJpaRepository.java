package way.application.data.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
		    :requestDate BETWEEN CAST(se.startTime AS date) AND CAST(se.endTime AS date)
		""")
	List<ScheduleEntity> findAcceptedSchedulesByMemberAndDate(@Param("memberSeq") Long memberSeq,
		@Param("requestDate") LocalDate requestDate);

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
			(se.startTime BETWEEN :startOfMonth AND :endOfMonth 			
			OR 			
			se.endTime BETWEEN :startOfMonth AND :endOfMonth)
			AND
			sme.acceptSchedule = true
			AND
			sme.invitedMember.memberSeq =:memberSeq
		""")
	List<ScheduleEntity> findSchedulesByYearMonth(@Param("startOfMonth") LocalDateTime startOfMonth,
		@Param("endOfMonth") LocalDateTime endOfMonth, @Param("memberSeq") Long memberSeq);
}
