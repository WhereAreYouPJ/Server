package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class ResetPasswordUseCase {

	private final MemberRepository memberRepository;

	public void invoke(Member.PasswordResetRequest request) {

		memberRepository.resetPassword(request);
	}
}
