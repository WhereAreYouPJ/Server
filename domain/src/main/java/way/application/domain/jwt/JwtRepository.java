package way.application.domain.jwt;

public interface JwtRepository {

    String generateAccessToken(String userId);
    String generateRefreshToken(String userId);
}
