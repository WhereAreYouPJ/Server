package way.application.domain.location.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.location.Location;
import way.application.domain.location.LocationRepository;

@Component
@RequiredArgsConstructor
public class DeleteLocationUseCase {
    private final LocationRepository locationRepository;

    public void invoke(Location.DeleteLocationRequest request) {
        locationRepository.delete(request);
    }
}
