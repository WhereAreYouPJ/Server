package way.application.domain.firebase.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.firebase.FireBaseRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class FcmMessageUseCase {
    private final FireBaseRepository fireBaseRepository;

    public void invoke(String targetToken, String title, String body) throws IOException {
        fireBaseRepository.sendMessageTo(targetToken, title, body);
    }
}
