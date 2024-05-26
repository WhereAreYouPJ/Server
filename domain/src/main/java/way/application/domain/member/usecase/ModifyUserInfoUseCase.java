package way.application.domain.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import way.application.domain.member.MemberRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ModifyUserInfoUseCase {

    private final MemberRepository memberRepository;

    public void invoke(Long memberId, MultipartFile multipartFile, String newUserId, String newUserName) throws IOException {

        memberRepository.modifyUserInfo(memberId,multipartFile,newUserId,newUserName);
    }

}
