package way.application.data.scheduleMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleMemberJpaRepository extends JpaRepository<ScheduleMemberEntity, Long> {

}
