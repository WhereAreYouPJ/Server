package way.application.domain.location.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.location.Location;
import way.application.domain.location.LocationRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetLocationUseCase {
    private final LocationRepository locationRepository;

    public List<Location.GetLocationResponse> invoke(Long memberId) {
        return locationRepository.get(memberId);
    }
}
