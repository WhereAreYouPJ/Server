package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class GetMemberDetailUseCase {

    private final MemberRepository memberRepository;

    public Member.GetMemberDetailResponse invoke(Long memberSeq) {

        return memberRepository.getMemberDetail(memberSeq);
    }

}
