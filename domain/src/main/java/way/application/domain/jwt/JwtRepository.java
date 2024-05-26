package way.application.domain.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtRepository {

    String generateAccessToken(String userId);
    String generateRefreshToken(String userId);
    String extractToken(HttpServletRequest request);
    String extractUserId(String token);

}
