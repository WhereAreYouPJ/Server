package way.application.data.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import way.application.data.utils.ValidateUtils;
import way.application.domain.jwt.JwtRepository;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder encoder;
    private final ValidateUtils validateUtils;
    private final JwtRepository jwtRepository;

    @Override
    @Transactional
    public void save(Member.SaveMemberRequest request) {
        memberJpaRepository.save(
                memberMapper.toMemberEntity(request, encoder.encode(request.password()))
        );
    }

    @Override
    @Transactional
    public Member.CheckIdResponse findByUserId(Member.CheckIdRequest request) {

        //예외처리
        validateUtils.validateMemberId(request.userId());

        return new Member.CheckIdResponse(request.userId());
    }

    @Override
    @Transactional
    public Member.CheckEmailResponse findByEmail(Member.CheckEmailRequest request) {

        validateUtils.validateEmail(request.email());

        return new Member.CheckEmailResponse(request.email());
    }

    @Override
    @Transactional
    public Member.MemberLoginResponse login(Member.MemberLoginRequest request) {

        // 검증
        MemberEntity member = validateUtils.validateUserId(request.userId());
        validateUtils.validatePassword(request.password(), member.getEncodedPassword());

        // fcm 토큰 저장
        memberJpaRepository.updateByFireBaseTargetToken(request.targetToken());

        // jwt 생성
        String accessToken = jwtRepository.generateAccessToken(request.userId());
        String refreshToken = jwtRepository.generateRefreshToken(request.userId());

        return new Member.MemberLoginResponse(accessToken,refreshToken, member.getUserId());
    }

    // TODO 로그인 시 MemberEntity firebaseTargetToken 저장 로직 구현 필요
}
