package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class CheckEmailUseCase {

    private final MemberRepository memberRepository;

    public Member.CheckEmailResponse invoke(String email) {

        return memberRepository.checkEmail(email);
    }
}
