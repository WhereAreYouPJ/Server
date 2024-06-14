package way.application.core.errorLogs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiErrorLogsJpaRepository extends JpaRepository<ApiErrorLogs, Long> {
}
