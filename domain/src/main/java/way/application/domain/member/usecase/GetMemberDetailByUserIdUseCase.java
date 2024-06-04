package way.application.domain.member.usecase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class GetMemberDetailByUserIdUseCase {

	private final MemberRepository memberRepository;

	public Member.GetMemberDetailByUserIdResponse invoke(String userId, HttpServletRequest request) {

		return memberRepository.getMemberDetailByUserId(userId, request);
	}

}
