package way.application.data.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final BCryptPasswordEncoder encoder;
    @Override
    public void save(Member.SaveMemberRequest request) {

        MemberEntity member = MemberEntity.builder()
                .userName(request.userName())
                .userId(request.userId())
                .password(encoder.encode(request.password()))
                .email(request.email())
                .profileImage(null)
                .build();

        memberJpaRepository.save(member);

    }
}
