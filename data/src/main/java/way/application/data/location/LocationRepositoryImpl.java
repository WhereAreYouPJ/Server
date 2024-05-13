package way.application.data.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.data.member.MemberEntity;
import way.application.data.utils.ValidateUtils;
import way.application.domain.location.Location;
import way.application.domain.location.LocationRepository;

import java.util.ArrayList;
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
        MemberEntity member = validateUtils.validateMemberEntity(request.memberId());
        validateUtils.validateLocationEntityByCount(request.memberId());

        LocationEntity locationEntity = locationJpaRepository.save(
                locationMapper.toLocationEntity(
                        request, member
                )
        );

        return new Location.SaveLocationResponse(
                locationEntity.getId()
        );
    }

    @Override
    public void delete(Location.DeleteLocationRequest request) {
        validateUtils.validateMemberEntity(request.memberId());
        validateUtils.validateLocationEntityById(request.locationId());
        validateUtils.validateLocationEntityByMemberId(request.locationId(), request.memberId());

        locationJpaRepository.deleteById(request.locationId());
    }

    @Override
    public List<Location.GetLocationResponse> get(Long memberId) {
        validateUtils.validateMemberEntity(memberId);

        List<LocationEntity> locationEntities =
                locationJpaRepository.findAllLocationEntitiesByMemberId(memberId);

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
