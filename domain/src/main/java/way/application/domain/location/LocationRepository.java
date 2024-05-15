package way.application.domain.location;

import java.util.List;

public interface LocationRepository {
    Location.SaveLocationResponse save(Location.SaveLocationRequest request);

    void delete(Location.DeleteLocationRequest request);

    List<Location.GetLocationResponse> get(Long memberId);
}
