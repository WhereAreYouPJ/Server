package way.application.domain.member;

public interface MemberRepository {

    void save (Member.SaveMemberRequest request);

    Member.CheckIdResponse checkId (String userId);

    Member.CheckEmailResponse checkEmail (String email);

    Member.MemberLoginResponse login (Member.MemberLoginRequest request);

    void sendMail (Member.MailSendRequest request);

    void verifyCode (Member.CodeVerifyRequest request);

    void verifyPasswordCode(Member.VerifyPasswordCodeRequest request);

    void resetPassword(Member.PasswordResetRequest request);

    Member.FindIdResponse findId (Member.FindIdRequest request);
}
