package way.application.data.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
	@Query("""
		SELECT 
		    le 
		FROM 
		   LocationEntity le 
		WHERE 
			le.member.memberSeq =:memberSeq
		""")
	List<LocationEntity> findLocationEntityByMemberId(@Param("memberSeq") Long memberSeq);

	@Query("""
		SELECT 
			ls 
		FROM 
			LocationEntity ls 
		WHERE 
			ls.member.memberSeq =:memberSeq 
			AND 
			ls.locationSeq =:locationSeq
		""")
	Optional<LocationEntity> findLocationEntityByMemberIdAndLocationId(@Param("memberSeq") Long memberSeq, @Param("locationSeq") Long locationSeq);

	@Query("""
		SELECT 
			ls 
		FROM 
			LocationEntity ls 
		WHERE 
			ls.member.memberSeq =:memberSeq
		""")
	List<LocationEntity> findAllLocationEntitiesByMemberId(@Param("memberSeq") Long memberSeq);
}
