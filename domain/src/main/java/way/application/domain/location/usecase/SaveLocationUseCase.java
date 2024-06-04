package way.application.domain.location.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.location.Location;
import way.application.domain.location.LocationRepository;

@Component
@RequiredArgsConstructor
public class SaveLocationUseCase {
	private final LocationRepository locationRepository;

	public Location.SaveLocationResponse invoke(Location.SaveLocationRequest request) {
		return locationRepository.save(request);
	}
}
