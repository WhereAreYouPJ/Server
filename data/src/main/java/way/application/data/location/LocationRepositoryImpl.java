package way.application.data.location;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.data.member.MemberEntity;
import way.application.data.utils.ValidateUtils;
import way.application.domain.location.Location;
import way.application.domain.location.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {
	private final LocationJpaRepository locationJpaRepository;
	private final LocationMapper locationMapper;
	private final ValidateUtils validateUtils;

	@Override
	public Location.SaveLocationResponse save(Location.SaveLocationRequest request) {
		MemberEntity member = validateUtils.validateMemberEntity(request.memberSeq());
		validateUtils.validateLocationEntityByCount(request.memberSeq());

		LocationEntity locationEntity = locationJpaRepository.save(
			locationMapper.toLocationEntity(
				request, member
			)
		);

		return new Location.SaveLocationResponse(
			locationEntity.getLocationSeq()
		);
	}

	@Override
	public void delete(Location.DeleteLocationRequest request) {
		validateUtils.validateMemberEntity(request.memberSeq());
		validateUtils.validateLocationEntityById(request.locationSeq());
		validateUtils.validateLocationEntityByMemberId(request.locationSeq(), request.memberSeq());

		locationJpaRepository.deleteById(request.locationSeq());
	}

	@Override
	public List<Location.GetLocationResponse> get(Long memberSeq) {
		validateUtils.validateMemberEntity(memberSeq);

		List<LocationEntity> locationEntities =
			locationJpaRepository.findAllLocationEntitiesByMemberId(memberSeq);

		return locationEntities.stream()
			.map(locationEntity -> new Location.GetLocationResponse(
				locationEntity.getLocation(),
				locationEntity.getStreetName(),
				locationEntity.getX(),
				locationEntity.getY()
			))
			.collect(Collectors.toList());
	}
}
