package way.application.domain.jwt;

public interface JwtRepository {

    String generateAccessToken(Long memberId);
    String generateRefreshToken(Long memberId);

}
