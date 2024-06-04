package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class FindIdUseCase {

	private final MemberRepository memberRepository;

	public Member.FindIdResponse invoke(Member.FindIdRequest request) {

		return memberRepository.findId(request);
	}

}
