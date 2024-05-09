package way.application.data.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
    @Query("SELECT se FROM ScheduleEntity se WHERE se.startTime <= :requestDate AND se.endTime >= :requestDate")
    List<ScheduleEntity> getScheduleEntityByRequestDate(@Param("requestDate") LocalDateTime requestDate);
}
