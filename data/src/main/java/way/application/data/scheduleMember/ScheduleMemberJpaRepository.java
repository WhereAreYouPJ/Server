package way.application.data.scheduleMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import way.application.data.schedule.ScheduleEntity;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Repository
public interface ScheduleMemberJpaRepository extends JpaRepository<ScheduleMemberEntity, Long> {
	void deleteAllBySchedule(ScheduleEntity scheduleEntity);

	@Query("""
		SELECT 
			sme 
		FROM 
			ScheduleMemberEntity sme 
		WHERE 
			sme.schedule =:scheduleEntity 
			AND 
			sme.acceptSchedule = true
		""")
	List<ScheduleMemberEntity> findAcceptedScheduleMemberByScheduleEntity(
		@Param("scheduleEntity") ScheduleEntity scheduleEntity);

	@Query("""
		SELECT 
			sme 
		FROM 
			ScheduleMemberEntity sme 
		WHERE 
			sme.schedule.scheduleSeq =:scheduleSeq 
			AND 
			sme.invitedMember.memberSeq =:memberSeq 
			AND 
			sme.acceptSchedule = true
		""")
	Optional<ScheduleMemberEntity> findAcceptedScheduleMemberByScheduleIdAndMemberId(
		@Param("scheduleSeq") Long scheduleSeq,
		@Param("memberSeq") Long memberSeq);

	@Query("""
			SELECT 
				sme
			FROM 
				ScheduleMemberEntity sme
			WHERE 
				sme.invitedMember.memberSeq =:memberSeq
				AND
				sme.schedule.scheduleSeq =:scheduleSeq
		""")
	Optional<ScheduleMemberEntity> findScheduleMemberEntityByMemberSeq(
		@Param("memberSeq") Long memberSeq,
		@Param("scheduleSeq") Long scheduleSeq
	);
}
