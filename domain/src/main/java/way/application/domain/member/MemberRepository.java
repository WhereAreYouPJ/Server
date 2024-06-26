package way.application.domain.member;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberRepository {

	void save(Member.SaveMemberRequest request);

	Member.CheckIdResponse checkId(String userId);

	Member.CheckEmailResponse checkEmail(String email);

	Member.MemberLoginResponse login(Member.MemberLoginRequest request);

	void sendMail(Member.MailSendRequest request);

	void sendMailByUserId(Member.MailSendByUserIdRequest request);

	void verifyCode(Member.CodeVerifyRequest request);

	void verifyPasswordCode(Member.VerifyPasswordCodeRequest request);

	void resetPassword(Member.PasswordResetRequest request);

	Member.FindIdResponse findId(Member.FindIdRequest request);

	Member.GetMemberDetailResponse getMemberDetail(Long memberSeq);

	void modifyUserInfo(Long memberSeq, MultipartFile multipartFile, String newUserId, String userName) throws
		IOException;

	Member.GetMemberDetailByUserIdResponse getMemberDetailByUserId(String userId, HttpServletRequest request);

	void logout(Member.LogoutRequest request);
}
