package way.application.domain.member;

public interface MemberRepository {

    void save (Member.SaveMemberRequest request);

    Member.CheckIdResponse checkId (Member.CheckIdRequest request);

    Member.CheckEmailResponse checkEmail (Member.CheckEmailRequest request);

    Member.MemberLoginResponse login (Member.MemberLoginRequest request);

    void sendMail (Member.MailSendRequest request);

    void verifyCode (Member.CodeVerifyRequest request);

    void verifyPasswordCode(Member.VerifyPasswordCodeRequest request);

}
