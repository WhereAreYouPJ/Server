package way.application.domain.member;

public interface MemberRepository {

    void save (Member.SaveMemberRequest request);

    Member.CheckIdResponse findByUserId (Member.CheckIdRequest request);

    Member.CheckEmailResponse findByEmail (Member.CheckEmailRequest request);
}
