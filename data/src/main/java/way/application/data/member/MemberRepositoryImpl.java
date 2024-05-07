package way.application.data.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void save(Member.SaveMemberRequest request) {
        memberJpaRepository.save(
                memberMapper.toMemberEntity(request, encoder.encode(request.password()))
        );
    }

    // TODO 로그인 시 MemberEntity firebaseTargetToken 저장 로직 구현 필요
}
