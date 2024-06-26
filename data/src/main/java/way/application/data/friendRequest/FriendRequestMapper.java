package way.application.data.friendRequest;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberMapper;
import way.application.domain.friend.Friend;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendRequestMapper {

    @Mapping(target = "friendRequestSeq", ignore = true)
    @Mapping(target = "senderSeq", source = "owner")
    @Mapping(target = "receiverSeq", source = "friends")
    @Mapping(target = "createTime", source = "localDateTime")
    FriendRequestEntity toFriendRequestEntity(MemberEntity owner, MemberEntity friends, LocalDateTime localDateTime);

}
