package way.application.domain.member.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class SaveMemberUseCase {

	private final MemberRepository memberRepository;

	public void invoke(Member.SaveMemberRequest request) {
		memberRepository.save(request);
	}

}
