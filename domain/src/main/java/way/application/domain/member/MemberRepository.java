package way.application.domain.member;

public interface MemberRepository {

    void save (Member.SaveMemberRequest request);

    Member.CheckIdResponse get (Member.CheckIdRequest request);
}
