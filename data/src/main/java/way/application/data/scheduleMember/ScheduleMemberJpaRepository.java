package way.application.data.scheduleMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import way.application.data.schedule.ScheduleEntity;

import java.util.List;

@Repository
public interface ScheduleMemberJpaRepository extends JpaRepository<ScheduleMemberEntity, Long> {
    void deleteAllBySchedule(ScheduleEntity scheduleEntity);

    List<ScheduleMemberEntity> findAllBySchedule(ScheduleEntity scheduleEntity);
}
