package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class LoginUseCase {

    private final MemberRepository memberRepository;

    public Member.MemberLoginResponse invoke(Member.MemberLoginRequest request) {

        return memberRepository.login(request);
    }
}
