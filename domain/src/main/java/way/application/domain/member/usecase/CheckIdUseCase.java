package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class CheckIdUseCase {

    private final MemberRepository memberRepository;

    public Member.CheckIdResponse invoke(String userId) {

        return memberRepository.checkId(userId);
    }
}
