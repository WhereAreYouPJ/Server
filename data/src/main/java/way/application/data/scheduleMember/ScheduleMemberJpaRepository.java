package way.application.data.scheduleMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import way.application.data.schedule.ScheduleEntity;

@Repository
public interface ScheduleMemberJpaRepository extends JpaRepository<ScheduleMemberEntity, Long> {
    void deleteAllBySchedule(ScheduleEntity scheduleEntity);
}
