package way.application.domain.firebase;

import java.io.IOException;

public interface FireBaseRepository {
    void sendMessageTo(String targetToken, String title, String body) throws IOException;
}
